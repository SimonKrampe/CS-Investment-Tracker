package com.csinvestmenttracker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class getPricing {
    
    public getPricing() {}
    public static void main(String[] args) {

        getPricing g = new getPricing();
        g.fetch("https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Danger%20Zone%20Case");

    }

    public double fetch(String link) {

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
        String [] s;
        s = temp.split(":");
        temp = s[2];
        s = temp.split("â");
        temp = s[0];
        temp = temp.substring(1);
        System.out.println(temp);
        return 0.0;

    }

}
