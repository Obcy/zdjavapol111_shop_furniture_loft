package com.loft.currency.service;

import com.loft.currency.model.CurrencyRate;
import com.loft.currency.model.Dto.CurrencyRateDto;
import com.loft.currency.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @Autowired
    private NBPGateway nbpGateway;


    @Override
    public void createCurrencyRate(String code) {
        CurrencyRateDto currencyRateDto = nbpGateway.getCurrencyRateFromExternalSource(code);
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setDate(currencyRateDto.getRates().get(0).getEffectiveDate());
        currencyRate.setCode(currencyRateDto.getCode());
        currencyRate.setCurrency(currencyRateDto.getRates().get(0).getAsk());
        currencyRateRepository.save(currencyRate);
    }

    @Override
    public List<CurrencyRate> getCurrentRateByDate(LocalDate date) {
        return currencyRateRepository.findAllByDate(date);
    }

    @Override
    public Optional<CurrencyRate> getCurrencyRateByDate(LocalDate date, String code) {
        return currencyRateRepository.findByDateAndCode(date, code);

    }

}