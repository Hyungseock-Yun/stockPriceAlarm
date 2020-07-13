package com.example.practice.kakao;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GetAccessTokenService {
    public void getAccessToken(String authorizationCode) {
        try {
            KakaoHttpClient.getAccessToken(authorizationCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
