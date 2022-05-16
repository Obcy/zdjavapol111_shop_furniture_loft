package com.loft.repository;

import com.loft.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    Optional<ShoppingCart> findById(Integer id);

    ShoppingCart findByUserId(Integer userId);

    boolean existsByUserId(Integer userId);


}
