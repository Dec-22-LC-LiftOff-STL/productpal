package org.launchcode.productpal.persistent.controllers;

import org.launchcode.productpal.persistent.models.Category;
import org.launchcode.productpal.persistent.models.data.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

//    @GetMapping("")
//    public String index (Model model){
//        model.addAttribute("title", "All Categories");
//        model.addAttribute("categories", categoryRepository.findAll());
//        return "categories/index";
//    }

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute("title", "Add Category");
        model.addAttribute(new Category());
        return "categories/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Category newCategory,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "categories/add";
        }
        categoryRepository.save(newCategory);
        return "redirect:";
    }

    @GetMapping("view/{categoryID}")
    public String displayViewCategory(Model model, @PathVariable int categoryID) {

        Optional<Category> optCategory = categoryRepository.findById(categoryID);
        if (optCategory.isPresent()) {
            Category category = (Category) optCategory.get();
            model.addAttribute("category", category);
            return "categories/view";
        }
        return "redirect:../";
        }
    }

