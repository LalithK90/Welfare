package lk.AVSEC.Welfare.asset.structure;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Structure {
    @GetMapping("/testV")
    public String template(){
        return "structure/testV";
    }


    @GetMapping("/testr")
    public String templatee() {
        return "structure/testr";
    }

    @GetMapping("/runTest")
    public String templated() {
        return "structure/runTest";
    }

    @GetMapping("/templateOriginal")
    public String templatec() {
        return "structure/templateOriginal";
    }

    @GetMapping("/main")
    public String templateb() {
        return "structure/main";
    }

    @GetMapping("/froms")
    public String templatea() {
        return "structure/froms";
    }

}


