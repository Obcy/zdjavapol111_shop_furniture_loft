package com.loft.controller;


import com.loft.currency.model.CurrencyRate;
import com.loft.currency.service.CurrencyRateService;
import com.loft.model.User;
import com.loft.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping(path = "/productList/{code}")
    public String productList(@PathVariable String code, ModelMap modelMap) {
        modelMap.addAttribute("products", productService.getAll(code));
        List<CurrencyRate> currencyRates = currencyRateService.getCurrentRateByDate(LocalDate.now());
        List<String> options = currencyRates.stream().map(currencyRate -> currencyRate.getCode()).collect(Collectors.toList());
        modelMap.addAttribute("options", options);;
        return "products";
    }
}

