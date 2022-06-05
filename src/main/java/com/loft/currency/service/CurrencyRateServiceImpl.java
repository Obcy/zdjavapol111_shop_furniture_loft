package com.loft.currency.service;

import com.loft.currency.model.CurrencyRate;
import com.loft.currency.model.Dto.CurrencyRateDto;
import com.loft.currency.repository.CurrencyRateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @Autowired
    private NBPGateway nbpGateway;

    @Autowired
    private HttpSession httpSession;

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
        CurrencyRate ratePLN = new CurrencyRate();
        ratePLN.setDate(LocalDate.now());
        ratePLN.setCode("PLN");
        ratePLN.setCurrency(BigDecimal.ONE);
        List<CurrencyRate> allByDate = currencyRateRepository.findAllByDate(date);
        allByDate.add(ratePLN);

        return allByDate;
    }

    @Override
    public Optional<CurrencyRate> getCurrencyRateByDate(LocalDate date, String code) {
        return currencyRateRepository.findByDateAndCode(date, code);

    }

    @Override
    public void switchDisplayCurrency(String code) {
        if (code.equals("PLN")) {
            httpSession.setAttribute("displayCurrency", code);
        }
        Optional<CurrencyRate> optionalCurrencyRate = getCurrencyRateByDate(LocalDate.now(), code);
        optionalCurrencyRate.ifPresent(currencyRate -> httpSession.setAttribute("displayCurrency", code));
    }

    @Override
    public String getDisplayCurrency() {
        if (httpSession.getAttribute("displayCurrency") == null) {
            return "PLN";
        } else {
            return (String) httpSession.getAttribute("displayCurrency");
        }
    }

    @Override
    public CurrencyRate findOrCreateCurrencyRate(LocalDate date) {
        String currencyCode = getDisplayCurrency();
        if ("PLN".equals(currencyCode)) {
            CurrencyRate ratePLN = new CurrencyRate();
            ratePLN.setDate(LocalDate.now());
            ratePLN.setCode("PLN");
            ratePLN.setCurrency(BigDecimal.ONE);
            return ratePLN;
        }
        Optional<CurrencyRate> optionalCurrencyRate = getCurrencyRateByDate(LocalDate.now(), currencyCode);
        if (optionalCurrencyRate.isEmpty()) {
            createCurrencyRate(currencyCode);
            optionalCurrencyRate = getCurrencyRateByDate(LocalDate.now(), currencyCode);
        }
        return optionalCurrencyRate.orElseThrow();

    }

}