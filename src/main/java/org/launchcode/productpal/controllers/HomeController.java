package org.launchcode.productpal.controllers;

import org.launchcode.productpal.models.Category;
import org.launchcode.productpal.models.CategoryService;
//import org.launchcode.productpal.models.Description;
import org.launchcode.productpal.models.data.CategoryRepository;
//import org.launchcode.productpal.models.data.DescriptionRepository;
import org.launchcode.productpal.models.data.ProductRepository;
import org.launchcode.productpal.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
//    @Autowired
//    private DescriptionRepository descriptionRepository;

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
    public String processAddProductForm(@ModelAttribute @Valid Product newProduct,Model model,
                                        Errors errors) {

//        , @RequestParam int categoryId

        if (errors.hasErrors()) {
            model.addAttribute(new Product());
            model.addAttribute("products", productRepository.findAll());
            model.addAttribute("categories", categoryRepository.findAll());
//            model.addAttribute("descriptions", descriptionRepository.findAll());
            return "add";
        }

//        Category category = new Category(newProduct.getCategory().getName());
//        categoryRepository.save(category);
//        newProduct.setCategory(category);
//        Category category = categoryRepository.findById(categoryId).orElse(new Category());
//        newProduct.setCategory(category);
        productRepository.save(newProduct);
        return "redirect:";
    }

    @GetMapping("view/{id}")
    public String displayViewProduct(Model model, @PathVariable Integer id) {

        Optional<Product> product = productRepository.findById(id);
        if (product != null) {
            model.addAttribute("title", "Product: "+ product.get().getName());
            model.addAttribute("product", product.get());
            return "view";
        } else {
            return "redirect:/";
        }
    }


}