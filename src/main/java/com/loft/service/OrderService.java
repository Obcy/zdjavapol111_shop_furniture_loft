package com.loft.service;

import com.loft.model.Order;
import com.loft.model.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    List<Order> findByUserId(Integer userId);

    Order findByOrderKey(String orderKey);

    void save(Order order);


    BigDecimal getTotal(Order order);

    void calculateDisplayPrice(Order order);

    void addItemsFromShoppingCart(Order order, ShoppingCart shoppingCart);
}
