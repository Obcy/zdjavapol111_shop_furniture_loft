package com.loft.service;

import com.loft.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    List<Product> findByPhrase(String search);

}
