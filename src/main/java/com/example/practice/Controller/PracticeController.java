package com.example.practice.Controller;

import com.example.practice.Util.StockUtil;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

@RestController
public class PracticeController {
    @GetMapping("/hello")
    public String hello() {
        return "Start Spring Boot";
    }

    @GetMapping("/name")
    public String name() {
        return "윤형석";
    }

    @GetMapping("/price")
    public String stockPrice() {
        String message = StockUtil.getStockMessage();
        return message;
    }
}