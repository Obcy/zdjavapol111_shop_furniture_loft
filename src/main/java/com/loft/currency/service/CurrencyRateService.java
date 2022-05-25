package com.loft.currency.service;

import com.loft.currency.model.CurrencyRate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CurrencyRateService {

    void createCurrencyRate(String code);
    List<CurrencyRate> getCurrentRateByDate(LocalDate date);
    Optional<CurrencyRate> getCurrencyRateByDate(LocalDate date, String code);

    void switchDisplayCurrency(String code);

    String getDisplayCurrency();
}