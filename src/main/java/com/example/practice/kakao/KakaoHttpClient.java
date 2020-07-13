package com.example.practice.kakao;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class KakaoHttpClient {
    private static String hostURL = "https://kauth.kakao.com";
    private static String apiKey = "a813d8398ab31e43715bd43a36673c37";
    private static String redirectURI = "http://localhost:8080/oauth";

    private KakaoHttpClient() {}

    public static void getAccessToken(String authorizationCode) throws IOException {
        OkHttpClient client = new OkHttpClient();
        HttpUrl urlWithParam = makeHttpUrlWithParam(authorizationCode);
        Request request = makeRequest(urlWithParam);

        System.out.println(client.newCall(request).execute().body().string());
    }

    private static Request makeRequest(HttpUrl url) {
        return new Request.Builder()
                .url(url)
                .build();
    }

    private static HttpUrl makeHttpUrlWithParam(String authorizationCode) throws MalformedURLException {
        HttpUrl.Builder httpBuilder = HttpUrl
                .get(new URL(hostURL + "/oauth/token"))
                .newBuilder();

        httpBuilder.addQueryParameter("grant_type", "authorization_code");
        httpBuilder.addQueryParameter("client_id", apiKey);
        httpBuilder.addQueryParameter("redirect_uri", redirectURI);
        httpBuilder.addQueryParameter("code", authorizationCode);

        return httpBuilder.build();
    }
}
