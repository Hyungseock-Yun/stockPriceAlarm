package com.example.practice.BatchJob;

import com.example.practice.Util.StockUtil;
import com.squareup.okhttp.*;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class kakaoAPI {
    public static void main (String[] arg) {
        String kakaoHost = "https://kapi.kakao.com";
        String token = "Bearer " + "kDi_Q8-O5mUB1ksvbWQcGS4CmdjXfP6IAOrtRQo9cpgAAAFzYCA3iw";
        String sendMsgRequestUrl = kakaoHost + "/v2/api/talk/memo/default/send";
        String sendMessage = StockUtil.getStockMessage();
        JSONObject sendObject = new JSONObject();
        JSONObject urlObject = new JSONObject();
        urlObject.put("web_url", "https://finance.naver.com");
        urlObject.put("mobile_web_url", "https://m.stock.naver.com");
        sendObject.put("object_type", "text");
        sendObject.put("text", sendMessage);
        sendObject.put("link", urlObject);
        sendObject.put("button_title", "눌러도 안나옴");

        System.out.println("sendObject = " + sendObject.toString());
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, sendObject.toString());
            HttpUrl.Builder httpBuilder = HttpUrl.get(new URL(sendMsgRequestUrl)).newBuilder();
            httpBuilder.addQueryParameter("template_object", sendObject.toString());
            Request request = new Request.Builder()
                    .url(httpBuilder.build())
                    .method("POST", body)
                    .addHeader("Authorization", token)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();

            System.out.println(response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
