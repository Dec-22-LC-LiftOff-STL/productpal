package org.launchcode.productpal.controllers;

import org.launchcode.productpal.models.Category;
import org.launchcode.productpal.models.CategoryService;
import org.launchcode.productpal.models.Product;
import org.launchcode.productpal.models.UserNotFoundException;
import org.launchcode.productpal.models.data.CategoryRepository;
import org.launchcode.productpal.models.data.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("categories")

public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("add")
    public String addCategory(Model model){
        model.addAttribute("category",new Category());
        model.addAttribute("pageTitle","Add category");
        model.addAttribute("name","Add category");
        return "categories/add";

    }
    @GetMapping("list")
    public String listCategory(Model model){
        List<Category> listCategory = categoryService.listCategory();
        model.addAttribute("listCategory",listCategory);

        return "categories/list";
    }
    @PostMapping("add")
    public String saveCategory(Category category){
        System.out.println(category);
        categoryService.save(category);
        return "redirect:/categories/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id")Integer id, Model model, RedirectAttributes ra){
        try {
            Category category=categoryService.get(id);
            model.addAttribute("category", category);
            model.addAttribute("pageTitle","Edit category");
            return "categories/add";
        } catch (UserNotFoundException e) {
            ra.addAttribute("message","category added successfully");
            return "redirect:/categories/list";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id")Integer id, Model model, RedirectAttributes ra){
        try {
            categoryService.deleteCategory(id);
        } catch (UserNotFoundException e) {
            ra.addAttribute("message","category added successfully");

        }
        return "redirect:/categories/list";
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

    @GetMapping("show/{id}")
    public String displayViewProduct(Model model, @PathVariable Integer id) {

        Optional<Category> category = categoryService.findByCategoryId(id);
        if (category != null) {
            model.addAttribute("title", "Category: "+ category.get().getName());
            model.addAttribute("category", category.get());
            return "categories/show";
        } else {
            return "redirect:/";
        }
    }
}