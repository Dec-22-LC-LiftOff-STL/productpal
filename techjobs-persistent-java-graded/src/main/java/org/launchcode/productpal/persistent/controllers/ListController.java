package org.launchcode.productpal.persistent.controllers;

import org.launchcode.productpal.persistent.models.Product;
import org.launchcode.productpal.persistent.models.ProductData;
import org.launchcode.productpal.persistent.models.data.CategoryRepository;
import org.launchcode.productpal.persistent.models.data.ProductRepository;
import org.launchcode.productpal.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

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
    private SkillRepository skillRepository;

    static HashMap<String, String> columnChoices = new HashMap<>();

    public ListController () {

        columnChoices.put("all", "All");
        columnChoices.put("category", "Category");
        columnChoices.put("skill", "Skill");

    }

    @RequestMapping("")
    public String list(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());

        return "list";
    }

    @RequestMapping(value = "products")
    public String listJobsByColumnAndValue(Model model, @RequestParam String column, @RequestParam String value) {
        Iterable<Product> products;
        if (column.toLowerCase().equals("all")){
            products = productRepository.findAll();
            model.addAttribute("title", "All Products");
        } else {
            products = ProductData.findByColumnAndValue(column, value, productRepository.findAll());
            model.addAttribute("title", "Products with " + columnChoices.get(column) + ": " + value);
        }
        model.addAttribute("products", products);

        return "list-products";
    }
}
