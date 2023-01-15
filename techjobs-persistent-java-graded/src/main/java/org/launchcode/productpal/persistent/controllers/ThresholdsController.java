package org.launchcode.productpal.persistent.controllers;

import org.launchcode.productpal.persistent.models.Product;
import org.launchcode.productpal.persistent.models.Thresholds;
import org.launchcode.productpal.persistent.models.data.ProductRepository;
import org.launchcode.productpal.persistent.models.data.ThresholdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("inventory")
public class ThresholdsController {

    @Autowired
    private ThresholdsRepository thresholdsRepository;

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("add")
    public String displayAddThresholdsForm(Model model){
        model.addAttribute("title", "Update Product Inventory");
        model.addAttribute("products", productRepository.findAll() );
        model.addAttribute("thresholds", new Thresholds());
        model.addAttribute("product", new Product());
        return "inventory/add";
    }

    @PostMapping("add")
    public String processAddThresholds(@ModelAttribute @Valid Thresholds thresholds,
                                       @RequestParam int name,
                                       Errors errors,
                                       Model model){
        if (errors.hasErrors()) {
            return "inventory/add";
        }

        Optional <Product> optProduct = productRepository.findById(name);
//        System.out.println("Right now it says" + optProduct.isPresent());

            if (optProduct.isPresent()) {
            Product product = (Product) optProduct.get();
            product.setAmount((Integer) model.getAttribute("amount"));
            model.addAttribute("product", productRepository.save(product));
            thresholds.setProduct(product);
//            System.out.println("ID: " + product.getId() + "Name: " + product.getName());
        }
//            System.out.println("ID: " + thresholds.getProduct().getId() + "Name: " + thresholds.getProduct().getName());

            if (thresholds.getProduct() != null || thresholds.getProduct().getId() > 0) {
            thresholdsRepository.save(thresholds);
        }


        return "redirect:";
    }
    @GetMapping("view/{productID}")
    public String displayViewProduct(Model model, @PathVariable int productID) {

        Optional<Product> optProduct = productRepository.findById(productID);
        if (optProduct.isPresent()) {
            Product product = (Product) optProduct.get();
            model.addAttribute("product", product);
            return "inventory/view";
        }
        return "redirect:../";
    }

}
