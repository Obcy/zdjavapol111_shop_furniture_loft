package com.loft.currency.model.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class RateDto {


    private String no;
    private LocalDate effectiveDate ;
    private BigDecimal bid;
    private BigDecimal ask;



}
