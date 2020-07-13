package com.example.practice.Util;

import com.example.practice.StockDto.StockDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class PropertyConfig {

    @Value("${stock}")
    String stock;

    @Bean
    public StockDto stockData() {
        StockDto stockData = new StockDto();
        stockData.setStockName(stock);
        return stockData;
    }
}
