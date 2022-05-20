package com.loft.repository;

import com.loft.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByTitleOrDescriptionContainingAllIgnoreCase(@Param("title") String title, @Param("description")String description);


}
