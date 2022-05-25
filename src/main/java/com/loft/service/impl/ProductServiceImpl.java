package com.loft.service.impl;

import com.loft.currency.model.CurrencyRate;
import com.loft.currency.service.CurrencyRateService;
import com.loft.model.Category;
import com.loft.model.Product;
import com.loft.repository.ProductRepository;
import com.loft.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CurrencyRateService currencyRateService;

    @Override
    public List<Product> getAll() {
        List<Product> products = productRepository.findAll();

        products.forEach(this::calculateDisplayPrice);
        return products;
    }

    private void calculateDisplayPrice(Product product) {
        String currencyCode = currencyRateService.getDisplayCurrency();
        if (currencyCode.equals("PLN")) {
            product.setDisplayPrice(product.getPrice());
            return;
        }
        LocalDate date = LocalDate.now();
        Optional<CurrencyRate> optionalCurrencyRate = currencyRateService.getCurrencyRateByDate(date, currencyCode);

        if (optionalCurrencyRate.isEmpty()) {
            currencyRateService.createCurrencyRate(currencyCode);
            optionalCurrencyRate = currencyRateService.getCurrencyRateByDate(date, currencyCode);
        }
        optionalCurrencyRate.ifPresent(currencyRate -> product.setDisplayPrice(product.getPrice().divide(currencyRate.getCurrency(), RoundingMode.CEILING)));

    }

    @Override
    public List<Product> findByPhrase(String search) {
        List<Product> products = productRepository.findByTitleOrDescriptionContainingAllIgnoreCase(search, search);
        products.forEach(this::calculateDisplayPrice);
        return products;
    }

    @Override
    public Product getById(Integer id) {
        Product product = productRepository.getById(id);
        calculateDisplayPrice(product);
        return product;
    }

    @Override
    public List<Product> getByCategory(Category category) {


        List<Product> products = productRepository.getAllByCategoryId(category.getId());

        products.addAll(category.getChildren().stream()
                .flatMap(child -> getByCategory(child).stream())
                .collect(Collectors.toList()));

        products.forEach(this::calculateDisplayPrice);
        return products;
    }


}
