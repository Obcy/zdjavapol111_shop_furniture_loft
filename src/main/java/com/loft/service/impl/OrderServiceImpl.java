package com.loft.service.impl;

import com.loft.model.Order;
import com.loft.model.OrderItem;
import com.loft.model.OrderStatus;
import com.loft.model.ShoppingCart;
import com.loft.repository.OrderRepository;
import com.loft.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order findByOrderKey(String orderKey) {
        return orderRepository.findByOrderKey(orderKey).orElseThrow();
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public BigDecimal getTotal(Order order) {
        return order.getOrderItems().stream()
                .map(orderItem -> orderItem.getDisplayPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public void calculateDisplayPrice(Order order) {
        order.getOrderItems().forEach(orderItem -> orderItem.setDisplayPrice(orderItem.getPrice().divide(order.getCurrencyRate(), RoundingMode.CEILING)));
    }

    @Override
    public void addItemsFromShoppingCart(Order order, ShoppingCart shoppingCart) {
        order.setOrderItems(shoppingCart.getCartItems().stream().map(shoppingCartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(shoppingCartItem.getQuantity());
                    orderItem.setProductName(shoppingCartItem.getProduct().getTitle());
                    orderItem.setPrice(shoppingCartItem.getProduct().getPrice());
                    orderItem.setDisplayPrice(shoppingCartItem.getProduct().getDisplayPrice());
                    return orderItem;  })
                .collect(Collectors.toSet()));
    }

}
