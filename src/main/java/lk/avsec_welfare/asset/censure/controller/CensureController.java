package lk.avsec_welfare.asset.censure.controller;

import lk.avsec_welfare.asset.censure.entitiy.Censure;
import lk.avsec_welfare.asset.censure.service.CensureService;
import lk.avsec_welfare.asset.censure_file.entity.CensureFiles;
import lk.avsec_welfare.asset.censure_file.service.CensureFilesService;
import lk.avsec_welfare.asset.dependent.service.DependentEmployeeService;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee.service.EmployeeFilesService;
import lk.avsec_welfare.asset.employee.service.EmployeeService;
import lk.avsec_welfare.asset.employee_working_place.service.EmployeeWorkingPlaceService;
import lk.avsec_welfare.asset.offence.controller.OffenceController;
import lk.avsec_welfare.asset.offence.entity.enums.OffenceType;
import lk.avsec_welfare.asset.offence.service.OffenceService;
import lk.avsec_welfare.asset.punishment.controller.PunishmentController;
import lk.avsec_welfare.asset.punishment.service.PunishmentService;
import lk.avsec_welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller

@RequestMapping("/censure")
public class CensureController  {
    private final CensureService censureService;
    private final CensureFilesService censureFilesService;
    private final EmployeeService employeeService;
    private final PunishmentService punishmentService;
    private final OffenceService offenceService;
    private final EmployeeFilesService employeeFilesService;
    private final EmployeeWorkingPlaceService employeeWorkingPlaceService;
    private final DependentEmployeeService dependentEmployeeService;

    public CensureController(CensureService censureService,
                             CensureFilesService censureFilesService, EmployeeService employeeService,
                             PunishmentService punishmentService, OffenceService offenceService,
                             EmployeeFilesService employeeFilesService,
                             EmployeeWorkingPlaceService employeeWorkingPlaceService,
                             DependentEmployeeService dependentEmployeeService) {
        this.censureService = censureService;
        this.censureFilesService = censureFilesService;
        this.employeeService = employeeService;
        this.punishmentService = punishmentService;
        this.offenceService = offenceService;
        this.employeeFilesService = employeeFilesService;
        this.employeeWorkingPlaceService = employeeWorkingPlaceService;
        this.dependentEmployeeService = dependentEmployeeService;
    }


    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("censures", censureService.findAll());
        return "censure/censure";
    }

    //When scr called file will send to
    @GetMapping( "/file1/{filename}" )
    public ResponseEntity< byte[] > downloadFile(@PathVariable( "filename" ) String filename) {
        CensureFiles file = censureFilesService.findByNewID(filename);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
            .body(file.getPic());
    }


    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("commendationDetail", censureService.findById(id));
        return "censure/censure-detail";
    }

    @GetMapping("/add/{id}")
    public String form(@PathVariable("id")Integer id , Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employeeDetail", employee);
        model.addAttribute("contendHeader", "Censure Add");
        model.addAttribute("addStatus", true);


        model.addAttribute("offenceTypes", OffenceType.values());

        model.addAttribute("censure", new Censure());
        model.addAttribute("punishmentFindUrl", MvcUriComponentsBuilder
            .fromMethodName(PunishmentController.class, "findByOffence", "")
            .toUriString());
        model.addAttribute("offenceUrl", MvcUriComponentsBuilder
            .fromMethodName(OffenceController.class, "findByOffenceType", "")
            .toUriString());
        model.addAttribute("punishmentUrl", MvcUriComponentsBuilder
            .fromMethodName(PunishmentController.class, "findByOffenceType", "")
            .toUriString());
        return "censure/addCensure";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Censure censure = censureService.findById(id);
        model.addAttribute("addStatus", false);
        model.addAttribute("offences", offenceService.findAll());
        model.addAttribute("punishments", punishmentService.findAll());
        model.addAttribute("censure",censure );
        model.addAttribute("contendHeader", "Censure Edit Details");
        model.addAttribute("employeeDetail",censure.getEmployee() );
        return "censure/addCensure";
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Censure censure, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addStatus", true);
            model.addAttribute("offences", offenceService.findAll());
            model.addAttribute("punishments", punishmentService.findAll());
            model.addAttribute("censure", censure);
            model.addAttribute("contendHeader", "Censure Add");
            model.addAttribute("employeeDetail", employeeService.findById(censure.getEmployee().getId()));
            return "censure/addCensure";
        }
        redirectAttributes.addFlashAttribute("commendationDetail", censureService.persist(censure));
        return "redirect:/censure";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        censureService.delete(id);
        return "redirect:/censure";
    }
}
