package com.openapitest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.openapitest.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


/**
 * @Description: 用戶介面控制類
 * @author: Eker
 * @date: 2023/5/27 下午 10:51
 * @version: V1.0
 */
@Controller
public class UserController {
    @Autowired
    ExchangeRateService exchangeRateService;

    @GetMapping("/")
    public String main(Model model) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates",String.class);
        Gson gson = new Gson();
        try {
            // 使用Gson將JSON字符串轉換為JAVA對象
            List<Map<String, String>> exchangeRates = gson.fromJson(responseEntity.getBody(), new TypeToken<List<Map<String, String>>>(){}.getType());
            //保存查詢匯率資料
            for (Map<String, String> rate : exchangeRates) {
                exchangeRateService.getExchangeRate(rate);
            }
            model.addAttribute("exchangeRates", exchangeRates);
        } catch (JsonSyntaxException e) {
            // 處理解析JSON數據時的異常
            e.printStackTrace();
            return "error";
        }
        return "index"; //view
    }

}