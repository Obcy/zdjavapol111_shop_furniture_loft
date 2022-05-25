package com.loft.service;

import com.loft.model.Category;
import com.loft.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    List<Product> findByPhrase(String search);

    Product getById(Integer id);

    List<Product> getByCategory(Category category);


}
