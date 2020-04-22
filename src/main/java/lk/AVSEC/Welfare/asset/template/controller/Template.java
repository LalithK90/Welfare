package lk.AVSEC.Welfare.asset.template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Template {
    @GetMapping("/template")
    public String template(){
        return "workingPlace/template";
    }
}
