package com.openapitest.controller;

import com.openapitest.entity.ExchangeRate;
import com.openapitest.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @Description: ExchangeRate控制類
 * @author: Eker
 * @date: 2023/5/28 上午 09:50
 * @version: V1.0
 */
@Controller
@RequestMapping("/api")
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService exchangeRateService;

    //依日期查詢匯率
    @GetMapping("/exchange-rate")
    @ResponseBody
    public ResponseEntity getExchangeRate(@RequestParam("date") String dateString, Model model) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Optional<ExchangeRate> exchangeRate = exchangeRateService.queryExchangeRateFromAPI(date);
        model.addAttribute("exchangeRate", exchangeRate.orElse(null));
        return ResponseEntity.status(HttpStatus.OK).body(exchangeRate.orElse(null));
//        return "index"; //view
    }
    //新增匯率資料
    @PostMapping("/exchange-rate")
    @ResponseBody
    public ResponseEntity addExchangeRate(@RequestBody ExchangeRate exchangeRate) {
        exchangeRateService.updateExchangeRate(exchangeRate.getDate(), exchangeRate.getUsdNtd(), exchangeRate.getRmbNtd(), exchangeRate.getUsdRmb());
        return ResponseEntity.status(HttpStatus.OK).body("保存成功");
    }
    //刪除匯率資料
    @DeleteMapping("/exchange-rate")
    @ResponseBody
    public ResponseEntity deleteExchangeRate(@RequestBody ExchangeRate exchangeRate) {
        exchangeRateService.deleteExchangeRate(exchangeRate.getDate());
        return ResponseEntity.status(HttpStatus.OK).body("數據已刪除");
    }
    //修改匯率資料
    @PutMapping("/exchange-rate")
    @ResponseBody
    public ResponseEntity updateExchangeRate(@RequestBody ExchangeRate exchangeRate) {
        exchangeRateService.updateExchangeRate(exchangeRate.getDate(), exchangeRate.getUsdNtd(), exchangeRate.getRmbNtd(), exchangeRate.getUsdRmb());
        return ResponseEntity.status(HttpStatus.OK).body("保存成功");
    }
}
