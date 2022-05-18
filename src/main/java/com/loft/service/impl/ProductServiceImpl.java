package com.loft.service.impl;

import com.loft.model.Product;
import com.loft.repository.ProductRepository;
import com.loft.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByPhrase(String search) {
        return productRepository.findByTitleContainingIgnoreCase(search);
    }


}
