package com.loft.currency.repository;


import com.loft.currency.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentRateRepository extends JpaRepository <CurrencyRate, Long> {

}
