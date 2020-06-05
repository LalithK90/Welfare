package lk.AVSEC.Welfare.asset.grievances.controller;


import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.grievanceStateChangeStateChange.service.GrievanceStateChangeService;
import lk.AVSEC.Welfare.asset.grievances.entity.Enum.GrievancesStatus;
import lk.AVSEC.Welfare.asset.grievances.entity.Enum.Priority;
import lk.AVSEC.Welfare.asset.grievances.entity.Enum.SolutionType;
import lk.AVSEC.Welfare.asset.grievances.entity.Grievance;
import lk.AVSEC.Welfare.asset.grievances.entity.GrievanceStateChange;
import lk.AVSEC.Welfare.asset.grievances.service.GrievancesService;
import lk.AVSEC.Welfare.asset.userManagement.service.UserService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import lk.AVSEC.Welfare.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/grievances")
public class GrievancesController implements AbstractController<Grievance, Integer> {
//todo there is something to change
    private final GrievancesService grievancesService;
    private final UserService userService;
    private final DateTimeAgeService dateTimeAgeService;
    private final GrievanceStateChangeService grievanceStateChangeService;

    @Autowired
    public GrievancesController(GrievancesService grievancesService, UserService userService, DateTimeAgeService dateTimeAgeService, GrievanceStateChangeService grievanceStateChangeService) {
        this.grievancesService = grievancesService;
        this.userService = userService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.grievanceStateChangeService = grievanceStateChangeService;
    }

    private String commonThing(Model model, Boolean booleanValue, Grievance grievanceObject) {

        model.addAttribute("priorities", Priority.values());
        model.addAttribute("addStatus", booleanValue);
        model.addAttribute("grievances", grievanceObject);
        return "grievances/addGrievances";
    }

    @GetMapping
    public String findAll(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = userService.findByUserName(userName).getEmployee();

        GrievancesStatus grievancesStatus;
        //log ing user -> switch
        //status check
        switch (employee.getBoardOfDirectors().toString()) {
            case "HOSS":
                grievancesStatus = GrievancesStatus.HOSS;
                break;
            case "DHOSS":
                grievancesStatus = GrievancesStatus.DHOSS;
                break;
            case "PRE":
                grievancesStatus = GrievancesStatus.PRE;
                break;
            default:
                grievancesStatus = GrievancesStatus.SCTY;
        }
        //date range (one month)
        LocalDate to = dateTimeAgeService.getCurrentDate();
        LocalDate form = to.minusDays(30);
//solution type, date range, grievance Status
        List<Grievance> toPending = grievancesService.findBySolutionTypeAndGrievancesStatusAndCreatedAtBetween(SolutionType.PR, grievancesStatus, dateTimeAgeService.dateTimeToLocalDateStartInDay(form), dateTimeAgeService.dateTimeToLocalDateEndInDay(to));
        List<Grievance> toClose = grievancesService.findBySolutionTypeAndGrievancesStatusAndCreatedAtBetween(SolutionType.CL, grievancesStatus, dateTimeAgeService.dateTimeToLocalDateStartInDay(form), dateTimeAgeService.dateTimeToLocalDateEndInDay(to));
        List<Grievance> personalToPending = grievancesService.findBySolutionTypeAndCreatedByAndCreatedAtBetween(SolutionType.PR, userName, dateTimeAgeService.dateTimeToLocalDateStartInDay(form), dateTimeAgeService.dateTimeToLocalDateEndInDay(to));
        List<Grievance> personalToClose = grievancesService.findBySolutionTypeAndCreatedByAndCreatedAtBetween(SolutionType.CL, userName, dateTimeAgeService.dateTimeToLocalDateStartInDay(form), dateTimeAgeService.dateTimeToLocalDateEndInDay(to));

        if (toPending.size() != 0) {
            model.addAttribute("toPending", toPending);
        }
        if (toClose.size() != 0) {
            model.addAttribute("toClose", toClose);
        }
        if (personalToPending.size() != 0) {
            model.addAttribute("personalToPending", personalToPending);
        }
        if (personalToClose.size() != 0) {
            model.addAttribute("personalToClose", personalToClose);
        }
        model.addAttribute("form", form);
        model.addAttribute("to", to);
        return "grievances/grievances";
    }

    @GetMapping("/add")
    public String form(Model model) {
        return commonThing(model, false, new Grievance());

    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("grievancesDetail", grievancesService.findById(id));
        return "grievances/grievances-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        return commonThing(model, true, grievancesService.findById(id));
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Grievance grievance, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, false, grievance);
        }
        if (grievance.getRemark() != null && grievance.getId() != null) {

            GrievanceStateChange grievanceStateChange = new GrievanceStateChange();
            grievanceStateChange.setRemark(grievance.getRemark());
            grievanceStateChange.setGrievance(grievance);
            grievanceStateChange.setCommentedBy(userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getEmployee().getCallingName());

            grievanceStateChangeService.persist(grievanceStateChange);
        }
        redirectAttributes.addFlashAttribute("grievancesDetail", grievancesService.persist(grievance));
        return "redirect:/grievances";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        grievancesService.delete(id);
        return "redirect:/grievances";
    }

    @GetMapping("/action/{id}")
    public String action(@PathVariable Integer id, Model model) {
        model.addAttribute("solutionTypes", SolutionType.values());
        model.addAttribute("grievancesStatuses", GrievancesStatus.values());
        model.addAttribute("actionDetail", true);
        return commonThing(model, true, grievancesService.findById(id));
    }

}
