package org.launchcode.productpal.persistent.controllers;

import org.launchcode.productpal.persistent.models.data.ProductRepository;
import org.launchcode.productpal.persistent.models.Product;
import org.launchcode.productpal.persistent.models.ProductData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import static org.launchcode.productpal.persistent.controllers.ListController.columnChoices;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        Iterable<Product> products;
        if (searchTerm.toLowerCase().equals("all") || searchTerm.equals("")){
            products = productRepository.findAll();
        } else {
            products = ProductData.findByColumnAndValue(searchType, searchTerm, productRepository.findAll());
        }
        model.addAttribute("columns", columnChoices);
        model.addAttribute("title", "Products with " + columnChoices.get(searchType) + ": " + searchTerm);
        model.addAttribute("products", products);

        return "search";
    }
}