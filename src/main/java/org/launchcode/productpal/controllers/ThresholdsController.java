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

//    @GetMapping("")
//    public String index (Model model){
//        model.addAttribute("title", "All Product Thresholds");
//        model.addAttribute("products", productRepository.findAll());
//        return "categories/index";
//    }



    @GetMapping("add")
    public String displayAddThresholdsForm(Model model){
        try {
            Iterable<Product> products = productRepository.findAll();


            model.addAttribute("title", "Update Product Inventory");
//            ProductThresholdsDTO newProductThresholds = new ProductThresholdsDTO();
//            model.addAttribute("newProductThresholds", newProductThresholds);
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
                productToSave.setName(product.getName());
                productToSave.setCategory(product.getCategory());
                productToSave.setAmount(product.getAmount());
//                newProductThresholds.setProduct(product);
                productToSave.setLowThreshold(product.getLowThreshold());
                productToSave.setHighThreshold(product.getHighThreshold());
                productRepository.save(productToSave);

//                if (product.getThresholds() == null) {
//                    Thresholds thresholds = new Thresholds(product,
//                            newProductThresholds.getThresholds().getLowThreshold(),
//                            newProductThresholds.getThresholds().getHighThreshold());
//                    newProductThresholds.setThresholds(thresholds);
//                    thresholdsRepository.save(thresholds);
//                } else {
//                    Optional <Thresholds> optThresholds = thresholdsRepository.findById(optProduct.get().getThresholds().getId());
//                    Thresholds thresholds = optThresholds.get();
//                    thresholds.setLowThreshold(newProductThresholds.getThresholds().getLowThreshold());
//                    thresholds.setHighThreshold(newProductThresholds.getThresholds().getHighThreshold());
//                    newProductThresholds.setThresholds(thresholds);
//                    thresholdsRepository.save(thresholds);
//                }
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
//                Optional<Thresholds> optThresholds = thresholdsRepository.findById(optProduct.get().getThresholds().getId());
//                model.addAttribute("thresholds", optThresholds.get());
                return "thresholds/view";
            }

//            ProductThresholdsDTO productThresholdDTO = new ProductThresholdsDTO();
            Optional<Product> optProduct = productRepository.findById(productID);
            if (optProduct.isPresent()) {
//                productThresholdDTO.setProduct(optProduct.get());
//                Optional <Thresholds> optThresholds = thresholdsRepository.findById(optProduct.get().getThresholds().getId());
//                productThresholdDTO.setThresholds(optThresholds.get());
                model.addAttribute("product", optProduct.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:../";
    }
}