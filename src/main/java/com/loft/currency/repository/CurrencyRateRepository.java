package com.loft.currency.repository;


import com.loft.currency.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends JpaRepository <CurrencyRate, Long> {

    Optional<CurrencyRate> findByDateAndCode(LocalDate date, String code);

    List<CurrencyRate> findAllByDate(LocalDate date);
}