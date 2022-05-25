package com.loft.controller;


import com.loft.currency.model.CurrencyRate;
import com.loft.currency.service.CurrencyRateService;
import com.loft.model.Category;
import com.loft.model.User;
import com.loft.service.CategoryService;
import com.loft.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CurrencyRateService currencyRateService;

    @Autowired
    private CategoryService categoryService;
  
    @GetMapping(path = "/search")
    public String searchProduct(ModelMap modelMap, @RequestParam(required = false, name = "search") String search) {
        log.info("search phrase " + search);
        modelMap.addAttribute("products", productService.findByPhrase(search));

        modelMap.addAttribute("searchPharse", search);

        addDefaultsToModelMap(modelMap);

        return "search";
      
    }

    @GetMapping(path = "/products")
    public String productList(ModelMap modelMap) {
        modelMap.addAttribute("products", productService.getAll());
        addDefaultsToModelMap(modelMap);

        List<Category> categories = categoryService.getParents();
        modelMap.addAttribute("categories", categories);

        modelMap.addAttribute("currentCategoryId", null);

        return "products";
    }

    @GetMapping(path = "/category/{id}")
    public String showProductsFromCategory(@PathVariable int id, ModelMap modelMap) {

        Category category = categoryService.getById(id);
        if (category == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Category not found"
            );
        }
        modelMap.addAttribute("currentCategoryId", id);

        modelMap.addAttribute("products", productService.getByCategory(category));

        addDefaultsToModelMap(modelMap);



        List<Category> categories = categoryService.getParents();
        modelMap.addAttribute("categories", categories);

        return "products";
    }

    private void addDefaultsToModelMap(ModelMap modelMap) {
        modelMap.addAttribute("displayCurrency", currencyRateService.getDisplayCurrency());
        List<CurrencyRate> currencyRates = currencyRateService.getCurrentRateByDate(LocalDate.now());
        List<String> options = currencyRates.stream().map(CurrencyRate::getCode).collect(Collectors.toList());
        modelMap.addAttribute("options", options);
    }

}

