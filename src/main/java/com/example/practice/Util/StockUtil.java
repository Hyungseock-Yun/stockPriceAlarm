package com.example.practice.Util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class StockUtil {
    public static String getHtml(String Url) {
        try {
        URL getInfoURL = new URL(Url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(getInfoURL.openStream(), "utf-8"));
            StringBuilder html = new StringBuilder();
            String current = "";
            while ((current = reader.readLine()) != null) {
                html.append(current);
            }
            reader.close();
            return html.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Url;
    }

    public static JSONObject parseXMLtoJSON(String xml) {
        JSONObject json = XML.toJSONObject(xml);
        return json;
    }

    public static String sendMessage() {
        String url = "http://asp1.krx.co.kr/servlet/krx.asp.XMLSiseEng?code=161890";
        // 081660 - 휠라홀딩스, 161890 - 한국콜마, 035720 - 카카오
        String html = getHtml(url);

        JSONObject json = parseXMLtoJSON(html);
        JSONObject stockPriceJSON = json.getJSONObject("stockprice").getJSONObject("TBL_StockInfo");
        String curStockPriceStr = stockPriceJSON.getString("CurJuka");
        String prevStockPriceStr = stockPriceJSON.getString("PrevJuka");
        float curStockPrice = (float)Integer.parseInt(curStockPriceStr.replace(",", ""));
        float prevStockPrice = (float)Integer.parseInt(prevStockPriceStr.replace(",", ""));
        float gapPercent = (curStockPrice - prevStockPrice) / prevStockPrice * 100;
        String priceGap = "";
        if (gapPercent > 0) {
            priceGap = "▲" + String.format("%.2f", gapPercent);
        } else {
            priceGap = "▽" + String.format("%.2f", gapPercent);
        }

        String sendMessage = "어제 주가 : " + prevStockPriceStr + "\r\n" + "현재 주가 : " + curStockPriceStr + "\r\n" + "등락률 : " + priceGap;
        System.out.println("카톡메세지 : " + sendMessage);

        return sendMessage;
    }

    public static String getProperties(String key) {
        String msg = "";
        try {
            String path = "C:/Program Files/workspace/practice/src/main/resources/stock.properties";
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(path);
            properties.load(new BufferedInputStream(fis));

            msg = properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static HashMap<String, String> getStockMap(String stock) {
        HashMap<String, String> stockMap = new HashMap();
        String[] stockArr = stock.split(",");
        for (String eachStock : stockArr) {
            String stockName = eachStock.split(":")[1];
            String stockCode = eachStock.split(":")[0];
            stockMap.put(stockCode, stockName);
        }
        return stockMap;
    }
}