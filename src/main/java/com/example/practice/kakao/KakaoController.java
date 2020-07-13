package com.example.practice.kakao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KakaoController {

    @Autowired
    private GetAccessTokenService kakaoService;

    @GetMapping("")
    public String main() {
        return "kakao/main";
    }

    @GetMapping("/oauth")
    public String getAuthCode(@RequestParam("code") String authorizationCode) {
        kakaoService.getAccessToken(authorizationCode);
        return "kakao/main";
    }
}
