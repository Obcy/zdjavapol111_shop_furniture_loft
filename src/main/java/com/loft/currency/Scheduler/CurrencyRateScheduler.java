package com.loft.currency.Scheduler;

import com.loft.currency.service.CurrentRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CurrencyRateScheduler {

    @Autowired
    private CurrentRateService currentRateService;

    @Scheduled(cron = "0 * * ? * *")
    public void getAcctualMeasured(){
        currentRateService.getAcctualCurrentRate();

    };


}
