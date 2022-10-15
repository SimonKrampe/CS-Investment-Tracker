package com.csinvestmenttracker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class fetch {
    
    public fetch() {}
    public static void main(String[] args) {

        fetch g = new fetch();
        
        // "https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Danger%20Zone%20Case"

        try {
            g.price("https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Gamma%202%20Case");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            g.volume("https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Danger%20Zone%20Case");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String[] all(String link) throws ParseException {

        JSONObject steamContent = fetchContent(link);

        String[] data = new String[2];
        data[0] = (String)steamContent.get("lowest_price");
        data[1] = (String)steamContent.get("volume");

        return data;
    }

    public String price(String link) throws ParseException {

        JSONObject steamContent = fetchContent(link);

        String price = (String)steamContent.get("lowest_price");
        System.out.println(price);

        return price;
    }

    public String volume(String link) throws ParseException {

        JSONObject steamContent = fetchContent(link);

        String volume = (String)steamContent.get("volume");
        System.out.println(volume);

        return volume;
    }

    public JSONObject fetchContent(String link) throws ParseException {

        StringBuilder content = new StringBuilder();

        try {
            
            URL steam = new URL(link);
            URLConnection connection = steam.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line + "\n");
            }
            reader.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        String temp = content.toString();

        JSONParser parser = new JSONParser();
        JSONObject parsed = (JSONObject)parser.parse(temp);

        return parsed;

    }

}
