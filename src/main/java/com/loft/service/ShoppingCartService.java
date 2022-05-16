package com.loft.service;

import com.loft.model.ShoppingCart;

import java.util.Optional;

public interface ShoppingCartService {

    ShoppingCart create();
    ShoppingCart get(int id);
    ShoppingCart getByUserId(int userId);
    void save(ShoppingCart shoppingCart);
    boolean update(ShoppingCart shoppingCart);

}
