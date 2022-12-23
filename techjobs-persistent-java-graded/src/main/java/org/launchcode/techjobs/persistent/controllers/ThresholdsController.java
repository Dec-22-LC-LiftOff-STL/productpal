package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Thresholds;
import org.launchcode.techjobs.persistent.models.data.ThresholdsRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("inventory")
public class ThresholdsController {

    @Autowired
    private ThresholdsRepository thresholdsRepository;

    @Autowired
    private JobRepository jobRepository;

//    @GetMapping("")
//    public String index (Model model){
//        model.addAttribute("title", "All Product Inventory");
//        model.addAttribute("thresholds", thresholdsRepository.findAll());
//        return "thresholds/index";
//    }

    @GetMapping("add")
    public String displayAddThresholdsForm(Model model){
        model.addAttribute("title", "Update Product Inventory");
        model.addAttribute("jobs", jobRepository.findAll() );
        model.addAttribute("job", new Job());
        model.addAttribute("thresholds", new Thresholds());
        return "inventory/add";
    }

    @PostMapping("add")
    public String processAddThresholds(@ModelAttribute @Valid Thresholds newThresholds,
                                       @RequestParam int jobID,
                                       Errors errors, Model model){
        if (errors.hasErrors()) {
            return "inventory/add";
        }
        thresholdsRepository.save(newThresholds);
        return "redirect:";
    }

}
