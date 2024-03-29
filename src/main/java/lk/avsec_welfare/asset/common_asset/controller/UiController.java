package lk.avsec_welfare.asset.common_asset.controller;

import lk.avsec_welfare.asset.briefing.service.BriefingService;
import lk.avsec_welfare.asset.userManagement.entity.User;
import lk.avsec_welfare.asset.userManagement.service.UserService;

import lk.avsec_welfare.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;

@Controller
public class UiController {

    private final UserService userService;
    private final DateTimeAgeService dateTimeAgeService;
    private final BriefingService briefingService;

    @Autowired
    public UiController(UserService userService, DateTimeAgeService dateTimeAgeService, BriefingService briefingService) {
        this.userService = userService;
        this.dateTimeAgeService = dateTimeAgeService;

        this.briefingService = briefingService;
    }

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping(value = {"/home", "/mainWindow"})
    public String getHome(Model model) {
        model.addAttribute("briefing", briefingService.findAll());
        //do some logic here if you want something to be done whenever
        /*User authUser = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        Set<Petition> petitionSet = new HashSet<>();
        minutePetitionService
                .findByEmployeeAndCreatedAtBetween(authUser.getEmployee(),
                        dateTimeAgeService
                                .dateTimeToLocalDateStartInDay(LocalDate.now()),
                        dateTimeAgeService
                                .dateTimeToLocalDateEndInDay(LocalDate.now())).forEach(
                minutePetition -> {
                    petitionSet.add(petitionService.findById(minutePetition.getPetition().getId()));
                });
        model.addAttribute("petitions", petitionSet.toArray());*/
        return "mainWindow";
    }

    @GetMapping(value = {"/login"})
    public String getLogin() {
        return "login/login";
    }

    @GetMapping(value = {"/login/error10"})
    public String getLogin10(Model model) {
        model.addAttribute("err", "You already entered wrong credential more than 10 times. \n Please meet the system" +
                " admin");
        return "login/login";
    }

    @GetMapping(value = {"/login/noUser"})
    public String getLoginNoUser(Model model) {
        model.addAttribute("err", "There is no user according to the user name. \n Please try again !!");
        return "login/login";
    }

}
