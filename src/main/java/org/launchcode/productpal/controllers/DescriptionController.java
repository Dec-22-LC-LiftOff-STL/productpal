package org.launchcode.productpal.controllers;

import javassist.runtime.Desc;
import org.launchcode.productpal.models.Category;
import org.launchcode.productpal.models.Description;
import org.launchcode.productpal.models.DescriptionService;
import org.launchcode.productpal.models.UserNotFoundException;
import org.launchcode.productpal.models.data.DescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("description")
public class DescriptionController {
    @Autowired
    private DescriptionService descriptionService;
//
//    @GetMapping("")
//    public String index (Model model){
//        model.addAttribute("title", "All Descriptions");
//        model.addAttribute("descriptions", descriptionRepository.findAll());
//        return "descriptions/index";
//    }


    @GetMapping("add")
    public String displayAddDescriptionForm(Model model) {
        model.addAttribute("title", "Add Description");
        model.addAttribute(new Description());
        return "description/add";
    }

    @GetMapping("view")
    public String listDescription(Model model){
        List<Description> listDescription = descriptionService.listDescription();
        model.addAttribute("listDescription",listDescription);

        return "description/view";
    }

    @PostMapping("add")
    public String saveDescription(Description description){
        System.out.println(description);
        DescriptionService.save(description);
        return "description/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id")Integer id, Model model, RedirectAttributes ra){
        try {
            Description description=descriptionService.get(id);
            model.addAttribute("description", description);
            model.addAttribute("pageTitle","Edit description");
            return "description/add";
        } catch (UserNotFoundException e) {
            ra.addAttribute("message","description added successfully");
            return "description/list";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteDescription(@PathVariable("id")Integer id, Model model, RedirectAttributes ra){
        try {
            descriptionService.deleteDescription(id);
        } catch (UserNotFoundException e) {
            ra.addAttribute("message","description added successfully");

        }
        return "description/list";
    }
}