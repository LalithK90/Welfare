package lk.AVSEC.Welfare.asset.a_shop_management.branch.controller;



import lk.AVSEC.Welfare.asset.a_shop_management.branch.entity.Branch;
import lk.AVSEC.Welfare.asset.a_shop_management.branch.service.BranchService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
@RequestMapping("/branch")
   public  class BranchController  implements AbstractController<Branch, Integer> {
        private final BranchService branchService;


    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    private String commonThings(Model model, Branch branch, Boolean addState) {
        model.addAttribute("branch", branch);
        model.addAttribute("addStatus", addState);
        model.addAttribute("contendHeader", "Add New Branch");
        return "branch/addBranch";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("branches", branchService.findAll());
        model.addAttribute("contendHeader", "Branch List");
        return "branch/branch";

    }

    @Override
    public String form(Model model) {
        return null;
    }


    @Override
    public String findById(Integer id, Model model) {
        return null;
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        return commonThings(model, new Branch(), true);
    }

    @PostMapping( value = {"/save", "/update"} )
    public String persist(@Valid @ModelAttribute Branch branch, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if ( bindingResult.hasErrors() ) {
            return commonThings(model, branch, true);
        }
        redirectAttributes.addFlashAttribute("branchDetail", branchService.persist(branch));
        // branchService.persist(branch);
        return "redirect:/branch";
    }

    @GetMapping( "/edit/{id}" )
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("contendHeader", "Edit Branch");
        return commonThings(model, branchService.findById(id), false);
    }

    @GetMapping( "/delete/{id}" )
    public String delete(@PathVariable Integer id, Model model) {
        branchService.delete(id);
        return "redirect:/branch";
    }

    @GetMapping( "/{id}" )
    public String view(@PathVariable Integer id, Model model) {
        model.addAttribute("branchDetail", branchService.findById(id));
        model.addAttribute("contendHeader", "Branch Details");
        return "branch/branch-detail";
    }
}

