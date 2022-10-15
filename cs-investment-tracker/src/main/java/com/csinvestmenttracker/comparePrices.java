package com.csinvestmenttracker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class comparePrices {

    
    fetch f = new fetch();
    userDatabase u = new userDatabase();

    public static void main(String[] args) {
        
        comparePrices c = new comparePrices();

        c.everything("1");

    }

    public double everything(String UID) {

        double profit = allBoxes(UID) + allPastBoxes(UID);
        System.out.println("Profit overall: "+ profit);

        return profit;
    }

    public double allPastBoxes(String UID) {

        JSONArray boxes = u.getUserPast(UID);

        double totalDifference = 0;

        for(int i = 0; i < boxes.size(); i++) {

            totalDifference = totalDifference + pastBox(boxes, i);

        }
        System.out.println("Total past Profit: " + totalDifference);
        return totalDifference;

    }

    public double allBoxes(String UID) {

        JSONArray boxes = u.getUser(UID);

        double totalDifference = 0;

        for(int i = 0; i < boxes.size(); i++) {

            try {
                totalDifference = totalDifference + specificBox(boxes, i);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        System.out.println("Total current Profit: "+totalDifference);
        return totalDifference;

    }

    public double pastBox(JSONArray boxes, int i) {

        JSONObject box = (JSONObject)boxes.get(i);
        int pcs = Integer.valueOf((String)box.get("pcs"));
        double price = Double.valueOf((String)box.get("price")) * pcs;
        double sellPrice = Double.valueOf((String)box.get("sellPrice")) * pcs;
        double difference = sellPrice - price;

        String url = (String)box.get("url");

        String[] temp = url.split("=");
        String name = temp[3];
        name = name.replace("%20", " ");

        String output = name + ": " + difference + " (" + price + ", " + sellPrice + ")";
        System.out.println(output);

        return difference;
    }

    public double specificBox(JSONArray boxes, int i) throws ParseException {

        JSONObject box = (JSONObject)boxes.get(i);

        String url = (String)box.get("url");
        url.replace("\\/", "/");
        String[] data = f.all(url);
        data[0] = data[0].replace("-", "0");
        data[0] = data[0].substring(0, data[0].length()-3);
        data[0] = data[0].replace(',', '.');

        double priceOld = Double.valueOf((String)box.get("price")) * Double.valueOf((String)box.get("pcs"));
        double priceNew = Double.valueOf(data[0]) * Double.valueOf((String)box.get("pcs"));
        double difference = priceNew - priceOld;

        String[] temp = url.split("=");
        String name = temp[3];
        name = name.replace("%20", " ");

        String output = name + ": " + difference + " (" + priceOld + ", " + priceNew + ")";
        System.out.println(output);

        return difference;
    }

}
