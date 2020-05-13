package lk.AVSEC.Welfare.asset.message.controller;

import lk.AVSEC.Welfare.asset.designation.entity.Designation;
import lk.AVSEC.Welfare.asset.designation.service.DesignationService;
import lk.AVSEC.Welfare.asset.employee.controller.EmployeeRestController;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.employee.service.EmployeeService;
import lk.AVSEC.Welfare.asset.message.entity.EmailMessage;
import lk.AVSEC.Welfare.asset.message.service.EmailMessageService;
import lk.AVSEC.Welfare.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/emailMessage")
public class EmailMessageController {
    private final EmailMessageService emailMessageService;
    private final DesignationService designationService;
    private final EmailService emailService;
    private final EmployeeService employeeService;

    @Autowired
    public EmailMessageController(EmailMessageService emailMessageService,
                                  DesignationService designationService, EmailService emailService, EmployeeService employeeService) {
        this.emailMessageService = emailMessageService;
        this.designationService = designationService;
        this.emailService = emailService;
        this.employeeService = employeeService;
    }

    //common things to employee and offender - start
    private void commonUrlBuilder(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("designations", designationService.findAll());
/*        model.addAttribute("provinces", Province.values());
        model.addAttribute("districtUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getDistrict", "")
                .build()
                .toString());
        model.addAttribute("stationUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getStation", "")
                .build()
                .toString());*/
        Object[] arg = {"designation", "id"};
        model.addAttribute("employeeUrl", MvcUriComponentsBuilder
                .fromMethodName(EmployeeRestController.class, "getEmployeeByWorkingPlace", arg)
                .build()
                .toString());
    }

    @GetMapping
    public String allMessage(Model model) {
        model.addAttribute("emailMessages", emailMessageService.findAll());
        return "emailMessage/emailMessage";
    }

    @GetMapping("/{id}")
    public String viewEmailMessage(@PathVariable Integer id, Model model) {
        model.addAttribute("emailMessageDetail", emailMessageService.findById(id));
        return "emailMessage/emailMessage-detail";
    }

    @GetMapping("/add")
    public String getMessageForm(Model model) {
        model.addAttribute("emailMessage", new EmailMessage());
        //url to find employee
        commonUrlBuilder(model);
        return "emailMessage/addEmailMessage";
    }

    @PostMapping("/add")
    public String sendEmailMessage(@Valid @ModelAttribute EmailMessage emailMessage, BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            model.addAttribute("emailMessage", emailMessage);
            commonUrlBuilder(model);
            return "emailMessage/addEmailMessage";
        }
        List<Employee> employees = new ArrayList<>();
        emailMessage.getEmployees().forEach(employee -> employees.add(employeeService.findById(employee.getId())));
        //if there is any duplicate it would remove using following
        emailMessage.setEmployees(employees.stream().distinct().collect(Collectors.toList()));

        // save email message to db and send it relevant employee
        EmailMessage emailMessageDb = emailMessageService.persist(emailMessage);
        //email subject
        String emailMessageDbSubject = emailMessageDb.getSubject();
        //email message
        String emailMessageDbMessage = emailMessageDb.getMessage();
        //send email all selected employee
        emailMessageDb.getEmployees().forEach(
                employee ->
                        emailService.sendEmail(
                                employee.getOfficeEmail(), emailMessageDbSubject, emailMessageDbMessage));

        return "redirect:/emailMessage/add";
    }
}
