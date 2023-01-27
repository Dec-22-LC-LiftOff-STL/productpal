package org.launchcode.productpal.controllers;

import org.launchcode.productpal.models.Product;
import org.launchcode.productpal.models.Thresholds;
import org.launchcode.productpal.models.data.ProductRepository;
import org.launchcode.productpal.models.data.ThresholdsRepository;
import org.launchcode.productpal.models.dto.ProductThresholdsDTO;
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
    public String processAddThresholds(@ModelAttribute @Valid Product product,
                                       Model model, Errors errors){
        if (errors.hasErrors()) {
            model.addAttribute("title", "Update Product Inventory");
            model.addAttribute("products", productRepository.findAll());
            model.addAttribute( "product", product);
            return "thresholds/add";
        }

        try {
            Iterable<Product> products = productRepository.findAll();
            Optional<Product> optProduct = productRepository.findById(product.getId());

            if (optProduct.isPresent()) {
                Product productToSave = optProduct.get();
                productToSave.setName(optProduct.get().getName());
                productToSave.setCategory(product.getCategory());
                productToSave.setAmount(product.getAmount());
                productToSave.setLowThreshold(product.getLowThreshold());
                productToSave.setHighThreshold(product.getHighThreshold());
                productRepository.save(productToSave);

            } else {
//                window.log("Product must be selected!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/";
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