package lk.avsec_welfare.asset.report;

import lk.avsec_welfare.asset.employee.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public class empReportController {

@Controller
@RequestMapping( "/report" )
public class EmpReportController {
    private final EmployeeService employeeService;

    public EmpReportController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    //1. main account service according to type
    @GetMapping( "/empReport" )
    public String mainAccountReport(Model model) {
        model.addAttribute("empReport", employeeService.findAll());
        return "report/empReport";
    }
}
}
