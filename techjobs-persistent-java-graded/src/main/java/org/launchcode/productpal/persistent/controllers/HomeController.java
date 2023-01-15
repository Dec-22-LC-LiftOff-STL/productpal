package org.launchcode.productpal.persistent.controllers;

import org.launchcode.productpal.persistent.models.Category;
import org.launchcode.productpal.persistent.models.Product;
import org.launchcode.productpal.persistent.models.Skill;
import org.launchcode.productpal.persistent.models.data.CategoryRepository;
import org.launchcode.productpal.persistent.models.data.ProductRepository;
import org.launchcode.productpal.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Products");
        model.addAttribute("products", productRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Product");
        model.addAttribute(new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("skills",skillRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Product newProduct,
                                       Errors errors, Model model, @RequestParam int categoryId, @RequestParam List<Integer> skills) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Product");
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("skills",skillRepository.findAll());
            return "add";
        }
        Optional<Category> optCategory = categoryRepository.findById(categoryId);

        if (optCategory.isPresent()) {
            Category category = optCategory.get();
            newProduct.setCategory(category);
        }
        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newProduct.setSkills(skillObjs);

        model.addAttribute("product", productRepository.save(newProduct));


        return "redirect:";
    }

    @GetMapping("view/{productId}")
    public String displayViewJob(Model model, @PathVariable int productId) {
        Optional<Product> result = productRepository.findById(productId);
        if (result.isPresent()) {
            Product product = result.get();
            model.addAttribute("product", product);
        }
        return "view";
    }


}
