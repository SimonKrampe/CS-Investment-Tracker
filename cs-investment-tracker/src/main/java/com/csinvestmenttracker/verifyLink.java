package com.csinvestmenttracker;

public class verifyLink {
    
    public static void main(String[] args) {

        verifyLink g = new verifyLink();

        g.check("https://steamcommunity.com/market/listings/730/2020%20RMR%20Contenders");

    }

    public verifyLink(){}

    public String check(String input) {

        if(input.contains("https://")) {
            input = fromLink(input);
        } else {
            input = fromName(input);
        }
        
        System.out.println(input);
        return input;
    }

    public String fromLink(String input) {

        String[] s;
        s=input.split("/");
        input = s[6];
        input = "https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=" + input;
        return input;

    }

    public String fromName(String input) {

        input = input.replace(" ", "%20");
        input = "https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=" + input;
        return input;

    }

}
