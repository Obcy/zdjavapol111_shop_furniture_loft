package com.loft.controller;

import com.loft.currency.model.CurrencyRate;
import com.loft.currency.service.CurrencyRateService;
import com.loft.model.ShoppingCart;
import com.loft.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CartController {

    @Autowired
    private CurrencyRateService currencyRateService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/cart")
    public String cartPage(ModelMap modelMap) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.addAttribute("currentUser", currentUser);

        ShoppingCart shoppingCart = shoppingCartService.createOrGet();
        modelMap.addAttribute("cartItems", shoppingCart.getCartItems());
        modelMap.addAttribute("totalPrice", shoppingCartService.getTotal());

        List<CurrencyRate> currencyRates = currencyRateService.getCurrentRateByDate(LocalDate.now());
        List<String> options = currencyRates.stream().map(currencyRate -> currencyRate.getCode()).collect(Collectors.toList());
        modelMap.addAttribute("options", options);

        return "cart";
    }

}
