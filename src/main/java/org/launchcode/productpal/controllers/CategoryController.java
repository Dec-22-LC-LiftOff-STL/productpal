package org.launchcode.productpal.controllers;

import org.launchcode.productpal.models.Category;
import org.launchcode.productpal.models.CategoryService;
import org.launchcode.productpal.models.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("categories")

public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("add")
    public String addCategory(Model model){
        model.addAttribute("category",new Category());
        model.addAttribute("pageTitle","Add category");
        return "categories/add";


    }
    @GetMapping("list")
    public String listCategory(Model model){
        List<Category> listCategory = categoryService.listCategory();
        model.addAttribute("listCategory",listCategory);

        return "categories/list";
    }

    @PostMapping("add")
    public String saveCategory(Category category, RedirectAttributes ra) {
        categoryService.save(category);
        ra.addFlashAttribute("message", "Category added successfully");
        return "redirect:list";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id")Integer id, Model model, RedirectAttributes ra){
        try {
            Category category=categoryService.get(id);
            model.addAttribute("category", category);
            model.addAttribute("pageTitle","Edit category");
            return "categories/edit";
        } catch (UserNotFoundException e) {
            ra.addAttribute("message","category added successfully");
            return "categories/list";
        }
    }

    @GetMapping("/list/{name}")
    public String showName(@PathVariable("name")Integer id, Model model, RedirectAttributes ra){
        try {
            Category category=categoryService.get(id);
            model.addAttribute("category", category);
            model.addAttribute("pageTitle","Edit category");
            return "categories/show";
        } catch (UserNotFoundException e) {
            ra.addAttribute("message","category added successfully");
            return "categories/list";
        }
    }

    @PostMapping("/delete")
    public String deleteCategory(@RequestParam("id")Integer id, RedirectAttributes ra){
        try {
            categoryService.deleteCategory(id);
            ra.addFlashAttribute("message", "Category deleted successfully");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/categories/list";
    }

}