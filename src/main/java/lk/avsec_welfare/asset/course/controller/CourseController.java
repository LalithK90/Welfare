package lk.avsec_welfare.asset.course.controller;

import lk.avsec_welfare.asset.course.entity.Course;
import lk.avsec_welfare.asset.course.service.CourseService;
import lk.avsec_welfare.asset.designation.entity.enums.CategoryType;
import lk.avsec_welfare.asset.designation.entity.enums.SalaryScale;
import lk.avsec_welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/course")
public class CourseController implements AbstractController<Course, Integer> {

  private final CourseService courseService;

  @Autowired
  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  private String commonThing(Model model, Boolean booleanValue, Course courseObject) {

//    model.addAttribute("salaryScales", SalaryScale.values());
//    model.addAttribute("categoryTypes", CategoryType.values());
    model.addAttribute("addStatus", booleanValue);
    model.addAttribute("contendHeader", "Add Course");
    model.addAttribute("course", courseObject);
    return "course/addCourse";
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("courses", courseService.findAll());
    model.addAttribute("contendHeader", "Courses");
    return "course/course";
  }

  @GetMapping("/add")
  public String form(Model model) {
    model.addAttribute("contendHeader", "Add Course");
    return commonThing(model, false, new Course());
  }

  @GetMapping("/{id}")
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("courseDetail", courseService.findById(id));
    return "course/course-detail";
  }

  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Integer id, Model model) {
    return commonThing(model, true, courseService.findById(id));
  }

  @PostMapping(value = {"/save", "/update"})
  public String persist(@Valid @ModelAttribute Course course, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if (bindingResult.hasErrors()) {
      return commonThing(model, false, course);
    }
    redirectAttributes.addFlashAttribute("courseDetail", courseService.persist(course));
    return "redirect:/course";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable Integer id, Model model) {
    courseService.delete(id);
    return "redirect:/course";
  }


}
