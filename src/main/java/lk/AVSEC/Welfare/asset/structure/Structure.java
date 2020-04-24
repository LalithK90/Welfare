package lk.AVSEC.Welfare.asset.structure;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Structure {
    @GetMapping("/testV")
    public String template(){
        return "structure/testV";
    }

}


