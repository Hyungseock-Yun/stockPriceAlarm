package com.example.practice.BatchJob;

public class kakaoAPI {
    public static void main (String[] arg) {
        String kakaoHost = "https://kauth.kakao.com";
        String clientID = "a813d8398ab31e43715bd43a36673c37";
        String tokenRequestUrl = kakaoHost + "/oauth/authorize?client_id="+ clientID +"&redirect_uri={redirect_uri}&response_type=code HTTP/1.1";

    }
}
