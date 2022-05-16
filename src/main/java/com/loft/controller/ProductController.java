package com.loft.controller;


import com.loft.model.User;
import com.loft.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path = "/productList")
    public String productList(ModelMap modelMap) {
        modelMap.addAttribute("Produkty", productService.getAll());
        return "products";
    }
}

