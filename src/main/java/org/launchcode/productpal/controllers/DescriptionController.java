package org.launchcode.productpal.controllers;

import org.launchcode.productpal.models.data.DescriptionRepository;
import org.launchcode.productpal.models.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("skills")
public class DescriptionController {
    @Autowired
    private DescriptionRepository descriptionRepository;

    @GetMapping("")
    public String index (Model model){
        model.addAttribute("title", "All Descriptions");
        model.addAttribute("descriptions", descriptionRepository.findAll());
        return "descriptions/index";
    }


    @GetMapping("add")
    public String displayAddDescriptionForm(Model model) {
        model.addAttribute("title", "Add Description");
        model.addAttribute(new Description());
        return "skills/add";
    }

    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Description newDescription,
                                      Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "skills/add";
        }
        descriptionRepository.save(newDescription);
        return "redirect:";
    }

    @GetMapping("view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable int descriptionId) {

        Optional optDescription = descriptionRepository.findById(descriptionId);
        if (optDescription.isPresent()) {
            Description description = (Description) optDescription.get();
            model.addAttribute("skill", description);
            return "skill/view";
        } else {
            return "redirect:../";
        }
    }
}