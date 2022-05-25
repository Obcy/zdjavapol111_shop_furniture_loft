package com.loft.repository;

import com.loft.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> getCategoryByParent(Integer id);

}
