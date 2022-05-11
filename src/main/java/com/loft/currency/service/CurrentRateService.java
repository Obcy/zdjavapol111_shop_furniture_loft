package com.loft.currency.service;

import com.loft.currency.model.CurrencyRate;
import com.loft.currency.model.Dto.CurrencyRateDto;

import java.time.LocalDate;
import java.util.List;

public interface CurrentRateService {

    void createCurrentRate(CurrencyRateDto currentRateDto);
    List<CurrencyRate> getCurrentRateByDate(LocalDate date);
    void getAcctualCurrentRate();
}
