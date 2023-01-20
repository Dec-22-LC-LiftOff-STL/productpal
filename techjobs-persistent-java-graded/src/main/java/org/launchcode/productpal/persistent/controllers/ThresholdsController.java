package org.launchcode.productpal.persistent.controllers;

import org.launchcode.productpal.persistent.models.Product;
import org.launchcode.productpal.persistent.models.Thresholds;
import org.launchcode.productpal.persistent.models.data.ProductRepository;
import org.launchcode.productpal.persistent.models.data.ThresholdsRepository;
import org.launchcode.productpal.persistent.models.dto.ProductThresholdsDTO;
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
    try {
     Iterable<Product> products = productRepository.findAll();


      if (products != null) {
        model.addAttribute("title", "Update Product Inventory");
        ProductThresholdsDTO newProductThresholds = new ProductThresholdsDTO();
        model.addAttribute("newProductThresholds", newProductThresholds);
        model.addAttribute("products", productRepository.findAll());
      }
    } catch (Exception e) {
        e.printStackTrace();
    }
      return "inventory/add";
    }

    @PostMapping("add")
    public String processAddThresholds(@ModelAttribute ProductThresholdsDTO newProductThresholds,
                                       @RequestParam int productName, @RequestParam int amount,
                                       @RequestParam int lowThresholds, @RequestParam int highThresholds,
                                       Model model, Errors errors){
        try {
             Iterable<Product> products = productRepository.findAll();


        if (errors.hasErrors()) {
            model.addAttribute("title", "Update Product Inventory");
            model.addAttribute("products", productRepository.findAll());
            model.addAttribute( "newProductThresholds", newProductThresholds);
            return "inventory/add";
        }

                Optional<Product> optProduct = productRepository.findById(productName);

                if (optProduct.isPresent()) {
                    Product product = optProduct.get();
                    product.setAmount(amount);
                    newProductThresholds.setProduct(product);
                    productRepository.save(newProductThresholds.getProduct());
                    Thresholds thresholds = new Thresholds(product, lowThresholds, highThresholds);
                    newProductThresholds.setThresholds(thresholds);
                    thresholdsRepository.save(newProductThresholds.getThresholds());
                } else {
                throw new Error("Product must be selected!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:";
    }

    @GetMapping("view/{productID}")
    public String displayViewProduct(Model model, @PathVariable int productID) {
        try {
            Optional<Product> optProduct = productRepository.findById(productID);
            if (optProduct.isPresent()) {
                Product product = (Product) optProduct.get();
                model.addAttribute("", product);
                return "inventory/view";
            }
        } catch (Exception e) {
        e.printStackTrace();
    }
            return "redirect:../";
    }
}
