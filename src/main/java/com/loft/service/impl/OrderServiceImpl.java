package com.loft.service.impl;

import com.loft.model.Order;
import com.loft.repository.OrderRepository;
import com.loft.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
                .map(orderItem -> orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

}
