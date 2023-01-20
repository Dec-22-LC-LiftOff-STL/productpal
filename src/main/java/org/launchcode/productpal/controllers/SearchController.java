package org.launchcode.productpal.controllers;

import org.launchcode.productpal.models.Product;
import org.launchcode.productpal.models.data.ProductRepository;
import org.launchcode.productpal.models.Product;
import org.launchcode.productpal.models.ProductData;
import org.launchcode.productpal.models.data.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import static org.launchcode.productpal.controllers.ListController.columnChoices;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("index")
public class SearchController {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "index";
    }

    //removed

    @PostMapping("results")
    public String displaySearchResults(Model model,  @RequestParam String searchType, @RequestParam String searchTerm){
        Iterable<Product> Products;
        if (searchTerm.toLowerCase().equals("all") || searchTerm.equals("")){
            Products = productRepository.findAll();
        } else {

            Products = ProductData.findByColumnAndValue(searchType, searchTerm, productRepository.findAll());
        }
        model.addAttribute("columns", columnChoices);
        model.addAttribute("title", "Product with " + columnChoices.get(searchType) + ": " + searchTerm);
        model.addAttribute("products", Products);

        return "index";
        //changed return search to index
    }
}