package com.openapitest.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @Description: RateExchange的容器類
 * @author: Eker
 * @date: 2023/5/28 下午 02:17
 * @version: V1.0
 */
@Entity
@Data
@Table(name = "exchangerates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double usdNtd;
    private Double rmbNtd;
    private Double usdRmb;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
