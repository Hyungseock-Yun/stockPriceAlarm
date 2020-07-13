package com.example.practice.BatchJob;

import com.example.practice.Util.StockUtil;
import org.json.JSONObject;
import org.json.XML;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class stockPriceBatch {
    public static void main (String[] arg) {
        String myStock = StockUtil.getProperties("stock");
        HashMap<String, String> stockMap = StockUtil.getStockMap(myStock);
        Set<String> stockKeys = stockMap.keySet();
        for (String stockCode : stockKeys) {
            String url = "http://asp1.krx.co.kr/servlet/krx.asp.XMLSiseEng?code="+stockCode;
            String html = StockUtil.getHtml(url);
            JSONObject json = XML.toJSONObject(html);
            JSONObject stockPriceJSON = json.getJSONObject("stockprice").getJSONObject("TBL_StockInfo");
            int curStockPriceInt = Integer.parseInt(stockPriceJSON.getString("CurJuka").replace(",", ""));
            int prevStockPriceInt = Integer.parseInt(stockPriceJSON.getString("PrevJuka").replace(",", ""));
            float curStockPrice = (float)curStockPriceInt;
            float prevStockPrice = (float)prevStockPriceInt;
            float gapPercent = (curStockPrice - prevStockPrice) / prevStockPrice * 100;
            String priceGap = "";
            if (gapPercent == 0) {
                priceGap = String.format("%.2f", gapPercent) + "%";
            } else if (gapPercent > 0){
                priceGap = "▲ " + String.format("%.2f", gapPercent) + "%";
            } else {
                priceGap = "▽ " + String.format("%.2f", gapPercent) + "%";
            }
            String curStockPriceStr = NumberFormat.getCurrencyInstance(Locale.KOREA).format(curStockPrice);
            String prevStockPriceStr = NumberFormat.getCurrencyInstance(Locale.KOREA).format(prevStockPrice);
            String sendMessage = "종목 : " + stockMap.get(stockCode) + "\n" + "어제 주가 : " + prevStockPriceStr + "\n" + "현재 주가 : " + curStockPriceStr + "\n" + "등락률 : " + priceGap;
            System.out.println(sendMessage);
        }
    }
}
