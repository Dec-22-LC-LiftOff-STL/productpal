package org.launchcode.productpal.controllers;

import org.launchcode.productpal.models.Description;
import org.launchcode.productpal.models.DescriptionService;
import org.launchcode.productpal.models.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("description")
public class DescriptionController {
    @Autowired
    private DescriptionService descriptionService;

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
        descriptionService.save(description);
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
            return "description/view";
        }
    }

    @GetMapping("/view/{name}")
    public String showName(@PathVariable("name")String name, Model model, RedirectAttributes ra){
        try {
            Description description = descriptionService.getByName(name);
            model.addAttribute("description", description);
            model.addAttribute("pageTitle","Edit description");
            return "description/add";
        } catch (UserNotFoundException e) {
            ra.addAttribute("message","description added successfully");
            return "description/view";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteDescription(@PathVariable("id")Integer id, Model model, RedirectAttributes ra){
        try {
            descriptionService.deleteDescription(id);
        } catch (UserNotFoundException e) {
            ra.addAttribute("message","description added successfully");

        }
        return "description/view";
    }
}