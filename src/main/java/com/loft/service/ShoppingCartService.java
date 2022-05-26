package com.loft.service;

import com.loft.model.Product;
import com.loft.model.ShoppingCart;

import java.math.BigDecimal;

public interface ShoppingCartService {

    ShoppingCart createOrGet();
    ShoppingCart getById(int id);
    ShoppingCart getByUserId(int userId);
    void save(ShoppingCart shoppingCart);

    void addProduct(Product product);

    void removeProduct(Product product);

    void changeProductQuantity(Product product, int quantity);

    void changeProductQuantityById(int id, int quantity);

    BigDecimal getTotal();

    void delete(ShoppingCart shoppingCart);

    void calculateDisplayPrice(ShoppingCart shoppingCart);
}
