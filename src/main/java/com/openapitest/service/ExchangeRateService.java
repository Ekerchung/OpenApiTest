package com.openapitest.service;

import com.openapitest.entity.ExchangeRate;
import com.openapitest.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: ExchangeRate的Service
 * @author: Eker
 * @date: 2023/5/28 下午 02:24
 * @version: V1.0
 */
@Service
public class ExchangeRateService {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    // 查詢匯率，若資料為新的資料，則保存到數據庫
    public void getExchangeRate(Map<String, String> rate) {
        String date = rate.get("Date");
        Double usdNtd = Double.valueOf(rate.get("USD/NTD"));
        Double rmbNtd = Double.valueOf(rate.get("RMB/NTD"));
        Double usdRmb = Double.valueOf(rate.get("USD/RMB"));
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, inputFormatter);
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findByDate(localDate);
        if (!optionalExchangeRate.isPresent()) {
            // 將查詢到的匯率儲存到資料庫
            saveExchangeRate(localDate, usdNtd, rmbNtd, usdRmb);
        }
    }

    // 儲存匯率
    public void saveExchangeRate(LocalDate date, Double usdNtd, Double rmbNtd, Double usdRmb) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setDate(date);
        exchangeRate.setUsdNtd(usdNtd);
        exchangeRate.setRmbNtd(rmbNtd);
        exchangeRate.setUsdRmb(usdRmb);
        exchangeRateRepository.save(exchangeRate);
    }

    // 查詢匯率
    public Optional<ExchangeRate> queryExchangeRateFromAPI(LocalDate date) {
        // 依照日期查詢匯率
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findByDate(date);
        return optionalExchangeRate;
    }

    // 刪除匯率
    public void deleteExchangeRate(LocalDate date) {
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findByDate(date);
        if (optionalExchangeRate.isPresent()) {
            exchangeRateRepository.delete(optionalExchangeRate.get());
        }
    }

    // 修改匯率
    public void updateExchangeRate(LocalDate date, Double usdNtd, Double rmbNtd, Double usdRmb) {
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findByDate(date);
        if (optionalExchangeRate.isPresent()) {
            //若日期已存在，修改資料
            ExchangeRate exchangeRate = optionalExchangeRate.orElse(null);
            exchangeRate.setUsdRmb(usdRmb);
            exchangeRate.setUsdNtd(usdNtd);
            exchangeRate.setRmbNtd(rmbNtd);
            exchangeRateRepository.save(exchangeRate);
        }else{ //若日期不存在，新增資料
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setDate(date);
            exchangeRate.setUsdRmb(usdRmb);
            exchangeRate.setUsdNtd(usdNtd);
            exchangeRate.setRmbNtd(rmbNtd);
            exchangeRateRepository.save(exchangeRate);
        }
    }
}
