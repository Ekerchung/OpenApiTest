package com.openapitest.repository;

import com.openapitest.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: ExchangeRateRepository 的接口
 * @author: Eker
 * @date: 2023/5/28 下午 02:23
 * @version: V1.0
 */
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findByDate(LocalDate date);
}
