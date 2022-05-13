package com.loft.currency.Scheduler;

import com.loft.currency.service.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CurrencyRateScheduler {

    @Autowired
    private CurrencyRateService currencyRateService;

    @Scheduled(cron = "0 * * ? * *")
    public void getActualMeasured(){

        List<String> currencies = List.of("USD", "GBP", "CHF");
        LocalDate date = LocalDate.now();
        currencies.forEach(currency-> {
            if(currencyRateService.getCurrencyRateByDate(date, currency).isEmpty()) {
                currencyRateService.createCurrencyRate(currency);
            }
        });

    };


}