package com.example.practice.Controller;

import com.example.practice.Util.StockUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        String message = StockUtil.sendMessage();
        System.out.println(message);
        return message;
    }
}
