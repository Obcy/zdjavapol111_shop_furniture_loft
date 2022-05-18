package com.loft.service.impl;

import com.loft.currency.model.CurrencyRate;
import com.loft.currency.service.CurrencyRateService;
import com.loft.model.Product;
import com.loft.repository.ProductRepository;
import com.loft.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CurrencyRateService currencyRateService;

    @Override
    public List<Product> getAll(String currencyCode) {
        List<Product> products = productRepository.findAll();
        if (currencyCode.equals("PLN")) {
            return products;
        }

        LocalDate date = LocalDate.now();

        Optional<CurrencyRate> optionalCurrencyRate = currencyRateService.getCurrencyRateByDate(date, currencyCode);

        if (optionalCurrencyRate.isEmpty()) {
            currencyRateService.createCurrencyRate(currencyCode);
            optionalCurrencyRate = currencyRateService.getCurrencyRateByDate(date, currencyCode);
        }
        CurrencyRate currencyRate = optionalCurrencyRate.get();
        products.stream().forEach(product -> product.setPrice(product.getPrice().divide(currencyRate.getCurrency(), RoundingMode.CEILING)));
        return products;
    }
}