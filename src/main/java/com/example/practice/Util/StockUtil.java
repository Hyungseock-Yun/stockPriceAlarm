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
import java.text.NumberFormat;
import java.util.*;
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

    public static String getProperties(String key) {
        String msg = "";
        try {
            String path = "src/main/resources/stock.properties";
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
            String stockCode = eachStock.split(":")[0];
            String stockName = eachStock.split(":")[1];
            stockMap.put(stockCode, stockName);
        }
        return stockMap;
    }

    public static String getStockMessage() {
        StringBuilder sendMessage = new StringBuilder();
        String myStock = StockUtil.getProperties("stock");
        HashMap<String, String> stockMap = StockUtil.getStockMap(myStock);
        Set<String> stockKeys = stockMap.keySet();
        for (String stockCode : stockKeys) {
            String url = "http://asp1.krx.co.kr/servlet/krx.asp.XMLSiseEng?code=" + stockCode;
            String html = StockUtil.getHtml(url);
            JSONObject json = XML.toJSONObject(html);
            JSONObject stockPriceJSON = json.getJSONObject("stockprice").getJSONObject("TBL_StockInfo");
            int curStockPriceInt = Integer.parseInt(stockPriceJSON.getString("CurJuka").replace(",", ""));
            int prevStockPriceInt = Integer.parseInt(stockPriceJSON.getString("PrevJuka").replace(",", ""));
            float curStockPrice = (float) curStockPriceInt;
            float prevStockPrice = (float) prevStockPriceInt;
            float gapPercent = (curStockPrice - prevStockPrice) / prevStockPrice * 100;
            String priceGap = "";
            if (gapPercent == 0) {
                priceGap = String.format("%.2f", gapPercent) + "%";
            } else if (gapPercent > 0) {
                priceGap = "▲ " + String.format("%.2f", gapPercent) + "%";
            } else {
                priceGap = "▽ " + String.format("%.2f", gapPercent) + "%";
            }
            String curStockPriceStr = NumberFormat.getCurrencyInstance(Locale.KOREA).format(curStockPrice);
            String prevStockPriceStr = NumberFormat.getCurrencyInstance(Locale.KOREA).format(prevStockPrice);
            sendMessage.append("종목 : " + stockMap.get(stockCode) + "\n" + "전일 종가 : " + prevStockPriceStr + "\n" + "현재 주가 : " + curStockPriceStr + "\n" + "등락률 : " + priceGap + "\n\n");
        }
        return sendMessage.toString();
    }
}
