package org.launchcode.productpal.controllers;

import org.launchcode.productpal.models.Product;
import org.launchcode.productpal.models.data.ProductRepository;
import org.launchcode.productpal.models.data.ThresholdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("thresholds")
public class ThresholdsController {

    @Autowired
    private ThresholdsRepository thresholdsRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("add")
    public String displayAddThresholdsForm(Model model){
        try {
            Iterable<Product> products = productRepository.findAll();
            model.addAttribute("title", "Update Product Inventory");
            model.addAttribute("products", productRepository.findAll());
            model.addAttribute("product", new Product());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "thresholds/add";
    }

    @PostMapping("add")
    public String processAddThresholdsForm(@ModelAttribute @Valid Product product, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Add Thresholds");
            model.addAttribute("products", productRepository.findAll());
            return "thresholds/add";
        }

        Optional<Product> originalProduct = productRepository.findById(product.getId());

        if(originalProduct.isPresent()){
            originalProduct.get().setAmount(product.getAmount());
            originalProduct.get().setLowThreshold(product.getLowThreshold());
            originalProduct.get().setHighThreshold(product.getHighThreshold());
            productRepository.save(originalProduct.get());
        }
        return "redirect:../";
    }

    @GetMapping("view/{productID}")
    public String displayViewProduct(Model model, @PathVariable int productID, Errors errors) {

        try {
            if (errors.hasErrors()) {
                model.addAttribute("title", "Product Threshold for Product ID: " +productID);
                Optional<Product> optProduct = productRepository.findById(productID);
                model.addAttribute( "product", optProduct.get());
                return "thresholds/view";
            }

            Optional<Product> optProduct = productRepository.findById(productID);
            if (optProduct.isPresent()) {
                model.addAttribute("product", optProduct.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:../";
    }
}