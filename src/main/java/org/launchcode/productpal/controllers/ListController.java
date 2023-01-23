package org.launchcode.productpal.controllers;

import org.launchcode.productpal.models.Product;
import org.launchcode.productpal.models.data.CategoryRepository;
import org.launchcode.productpal.models.data.ProductRepository;
import org.launchcode.productpal.models.ProductData;
import org.launchcode.productpal.models.data.DescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DescriptionRepository descriptionRepository;

    static HashMap<String, String> columnChoices = new HashMap<>();

    public ListController () {

        columnChoices.put("all", "All");
        columnChoices.put("category", "Category");
        columnChoices.put("description", "Description");

    }

    @RequestMapping("")
    public String list(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("descriptions", descriptionRepository.findAll());

        return "list";
    }

    @RequestMapping(value = "products")
    public String listProductsByColumnAndValue(Model model, @RequestParam String column, @RequestParam String value) {
        Iterable<Product> products;
        if (column.equals("all")){
            products = ProductData.findByColumnAndValue(column, value, productRepository.findAll());
            model.addAttribute("title", "All Products");
        } else {
            products = ProductData.findByColumnAndValue(column, value, productRepository.findAll());
            model.addAttribute("title", "Products with " + columnChoices.get(column) + ": " + value);
        }
        List<Product> productsList = (List<Product>) products;
        System.out.println("Number of products retrieved: " + productsList.size());
        model.addAttribute("products", products);
        return "list-products";
    }

    @RequestMapping(value = "category/{name}")
    public String listProductsByCategory(Model model, @PathVariable String name) {
        Iterable<Product> products = productRepository.findByCategoryName(name);
        model.addAttribute("products", products);
        model.addAttribute("title", "Products in category: " + name);
        return "categories/list";
    }

}