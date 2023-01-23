package org.launchcode.productpal.controllers;

import org.launchcode.productpal.models.CategoryService;
import org.launchcode.productpal.models.data.CategoryRepository;
import org.launchcode.productpal.models.data.ProductRepository;
import org.launchcode.productpal.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("add")
    public String displayAddProductForm(Model model) {
        model.addAttribute(new Product());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddProductForm(@ModelAttribute @Valid Product newProduct, Model model,
                                    Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute(new Product());
            model.addAttribute("products", productRepository.findAll());
            model.addAttribute("categories", categoryRepository.findAll());
            return "add";
        }

        productRepository.save(newProduct);
        return "redirect:";
    }

    @GetMapping("view/{name}")
    public String displayViewProduct(Model model, @PathVariable String name) {

        Product product = productRepository.findFirstByName(name);
        if (product != null) {
            model.addAttribute("product", product);
            return "view";
        } else {
            return "redirect:/";
        }
    }


}