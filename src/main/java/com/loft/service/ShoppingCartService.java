package com.loft.service;

import com.loft.model.Product;
import com.loft.model.ShoppingCart;

import java.math.BigDecimal;
import java.util.Optional;

public interface ShoppingCartService {

    ShoppingCart create();
    ShoppingCart get(int id);
    ShoppingCart getByUserId(int userId);
    void save(ShoppingCart shoppingCart);

    void addProduct(Product product);

    void removeProduct(Product product);

    void changeProductQuantity(Product product, int quantity);

    BigDecimal getTotal();

}
