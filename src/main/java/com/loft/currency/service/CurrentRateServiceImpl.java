package com.loft.currency.service;

import com.loft.currency.model.CurrencyRate;
import com.loft.currency.model.Dto.CurrencyRateDto;
import com.loft.currency.repository.CurrentRateRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CurrentRateServiceImpl implements CurrentRateService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrentRateRepository currentRateRepository;

    @Value("${loft.api.url}")
    private String apiUrl;

    @Override
    public void createCurrentRate(CurrencyRateDto currentRateDto) {

    }

    @Override
    public List<CurrencyRate> getCurrentRateByDate(LocalDate date) {
        return null;
        }

    @Override
    public void getAcctualCurrentRate() {
        CurrencyRateDto currencyRateDto = getCurrencyRate("USD");
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setDate(currencyRateDto.getRates().get(0).getEffectiveDate());
        currencyRate.setCode(currencyRateDto.getCode());
        currencyRate.setCurrency(currencyRateDto.getRates().get(0).getAsk());
        currentRateRepository.save(currencyRate);
    }

    @SneakyThrows
    private CurrencyRateDto getCurrencyRate(String code) {
        try {
            return callGetMethod(apiUrl, CurrencyRateDto.class, code);
        } catch (Exception e) {
            e.printStackTrace();//throw new CountryValidationException(String.format("Date not supported"));
        }
        return null;
    }

    private <T> T callGetMethod(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(url, responseType, objects);
    }


}


