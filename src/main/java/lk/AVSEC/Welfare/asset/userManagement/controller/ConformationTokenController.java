package lk.AVSEC.Welfare.asset.userManagement.controller;

import lk.AVSEC.Welfare.asset.userManagement.entity.ConformationToken;
import lk.AVSEC.Welfare.asset.userManagement.service.ConformationTokenService;
import lk.AVSEC.Welfare.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
@RequestMapping("/register")
public class ConformationTokenController {
    private final ConformationTokenService conformationTokenService;
    private final EmailService emailService;

    @Autowired
    public ConformationTokenController(ConformationTokenService conformationTokenService, EmailService emailService) {
        this.conformationTokenService = conformationTokenService;
        this.emailService = emailService;
    }

    @GetMapping
    private String sendEmailForm() {
        return "user/register";
    }

    @PostMapping("/token")
    private String sendTokenToEmail(@RequestParam("email") String email, Model model) {
        //todo-> before create the token need to check there is user on current email
        // if not create token else send forgotten password form to fill


        ConformationToken conformationToken = new ConformationToken(email);
        String url = MvcUriComponentsBuilder.fromMethodName(ConformationTokenController.class,
                "passwordEnterPage", "").build().toUriString();

        emailService.sendEmail(email, "Email Verification (AVSEC-WMS) - Not reply",
                "Please click below link to active your account \n\t".concat(url + conformationTokenService.createToken(conformationToken).getToken()).concat("\n  this link is valid only one day. "));
        model.addAttribute("successMessage", "Please check your email \n Your entered email is \t ".concat(email));
        return "user/successMessage";
    }

    @GetMapping("/active/{token}")
    public String passwordEnterPage(@PathVariable("token") String token) {
        //todo-> need to check token has valid or not
        // if valid send password enter from with email
        // else send email enter form
        System.out.println("token " + token);
        // conformationTokenService.findByToken(token);
        return "user/password";
    }
}
