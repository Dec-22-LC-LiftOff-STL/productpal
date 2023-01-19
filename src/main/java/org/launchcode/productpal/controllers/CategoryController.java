package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.productpal.models.Category;
import org.launchcode.productpal.models.CategoryService;
import org.launchcode.productpal.models.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("category")

public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("add")
    public String addCategory(Model model){
        model.addAttribute("category",new Category());
        model.addAttribute("pageTitle","Add category");
        return "category/add";


    }
    @GetMapping("list")
    public String listCategory(Model model){
        List<Category> listCategory = categoryService.listCategory();
        model.addAttribute("listCategory",listCategory);

        return "category/list";
    }
    @PostMapping("add")
    public String saveCategory(Category category){
        System.out.println(category);
        categoryService.save(category);
        return "redirect:/category/list";
    }
   /* @GetMapping("/category/edit/{id}")
    public ModelAndView showEditCategoryPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_category");
       Category category =  categoryService.get(id);
        mav.addObject("category", category);

        return mav;
    }*/
   @GetMapping("/edit/{id}")
   public String showEditForm(@PathVariable("id")Integer id, Model model, RedirectAttributes ra){
       try {
           Category category=categoryService.get(id);
           model.addAttribute("category", category);
           model.addAttribute("pageTitle","Edit category");
           return "category/add";
       } catch (UserNotFoundException e) {
           ra.addAttribute("message","category added successfully");
           return "redirect:/category/list";
       }
   }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id")Integer id, Model model, RedirectAttributes ra){
        try {
            categoryService.deleteCategory(id);
        } catch (UserNotFoundException e) {
            ra.addAttribute("message","category added successfully");

        }
        return "redirect:/category/list";
    }
}
