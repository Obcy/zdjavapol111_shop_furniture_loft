package com.loft.currency.service;

import com.loft.currency.model.Dto.CurrencyRateDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NBPGateway {

    @Value("${loft.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @SneakyThrows
    public CurrencyRateDto getCurrencyRateFromExternalSource(String code) {
        try {
            return callGetMethod(apiUrl, CurrencyRateDto.class, code);
        } catch (Exception e) {
            throw new RuntimeException("Currency not found");
        }
    }

    private <T> T callGetMethod(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(url, responseType, objects);
    }

}