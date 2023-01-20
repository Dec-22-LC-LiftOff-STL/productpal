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
        model.addAttribute("categories", categoryRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddProductForm(@ModelAttribute @Valid Product newProduct,
                                    Errors errors) {

        if (errors.hasErrors()) {
            return "add";
        }

        productRepository.save(newProduct);
        return "redirect:";
    }

    @GetMapping("view/{productId}")
    public String displayViewProduct(Model model, @PathVariable int productId) {

        Optional optProduct = productRepository.findById(productId);
        if (!optProduct.isEmpty()) {
            Product product = (Product) optProduct.get();
            model.addAttribute("product", product);
            return "list";
        } else {
            return "redirect:/";
        }
    }


}