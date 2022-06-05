package com.loft.controller;

import com.loft.model.ShoppingCart;
import com.loft.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/api/cart/updateItemQuantity")
    public void updateItemQuantity(@RequestParam(value = "id", required = true) int id,
                                   @RequestParam(value = "quantity", required = true) int quantity) {
        shoppingCartService.changeProductQuantityById(id, quantity);
    }

}
