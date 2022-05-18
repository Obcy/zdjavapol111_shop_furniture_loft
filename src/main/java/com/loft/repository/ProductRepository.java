package com.loft.repository;

import com.loft.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByTitleContainingIgnoreCase(String title);


}
