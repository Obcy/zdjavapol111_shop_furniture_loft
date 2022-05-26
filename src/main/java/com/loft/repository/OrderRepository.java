package com.loft.repository;

import com.loft.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findByOrderKey(@Param("order_key") String orderKey);

    List<Order> findByUserId(@Param("user_id") Integer userId);
}
