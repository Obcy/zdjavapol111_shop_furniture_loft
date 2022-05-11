package com.loft.currency.model.Dto;

import lombok.Data;

import java.util.List;

@Data
public class CurrencyRateDto {

    private String tableC;
    private String currency;
    private String code;
    private List<RateDto> rates;




}
