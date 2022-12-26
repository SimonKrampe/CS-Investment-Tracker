package com.csinvestmenttracker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class fetch {

    public static void main(String[] args) {

        fetch g = new fetch();
        
        // "https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Danger%20Zone%20Case"

        try {
            g.price("https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Gamma%202%20Case");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Fetches the current price of a box and the current volume of boxes being sold on steam 
     * @param link ("https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=xxx%20Case")
     * @return String array containing price (index 0) and volume of boxes being sold (index 1)
     */
    public String[] all(String link) throws ParseException {

        JSONObject steamContent = fetchContent(link);

        String[] data = new String[2];
        data[0] = (String)steamContent.get("lowest_price");
        data[1] = (String)steamContent.get("volume");

        data[0] = data[0].replace("-", "0");
        data[0] = data[0].substring(0, data[0].length()-3);
        data[0] = data[0].replace(',', '.');

        return data;
    }

    /**
     * Fetches the price of a box 
     * @param link ("https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=xxx%20Case")
     * @return Price of a box as a double
     */
    public double price(String link) throws ParseException {

        JSONObject steamContent = fetchContent(link);

        String price = (String)steamContent.get("lowest_price");
        price = price.replace("-", "0");
        price = price.replace("\u20AC", "");
        price = price.replace(',', '.');
        System.out.println(price);
       
        double priceValue = Double.valueOf(price);

        return priceValue;
    }

    /**
     * Fetches the current volume that is being sold of a box 
     * @param link ("https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=xxx%20Case")
     * @return Volume of a box as an Int
     */
    public int volume(String link) throws ParseException {

        JSONObject steamContent = fetchContent(link);

        String volume = (String)steamContent.get("volume");
        System.out.println(volume);

        int volumeValue = Integer.valueOf(volume);

        return volumeValue;
    }

    /**
     * Fetches from steam and only works with links of a given format
     * @param link ("https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=xxx%20Case")
     * @return Parsed JSONObject ({"success":true,"lowest_price":"1,--€","volume":"11,855","median_price":"1,01€"})
     */
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
