package com.csinvestmenttracker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class comparePrices {

    
    fetch f = new fetch();
    userDatabase u = new userDatabase();

    public static void main(String[] args) {
        
        comparePrices c = new comparePrices();

        c.allBoxes("1");

    }

    public void allBoxes(String UID) {

        JSONArray boxes = u.getUser(UID);

        double totalDifference = 0;

        for(int i = 0; i < boxes.size(); i++) {

            try {
                totalDifference = totalDifference + specificBox(boxes, UID, i);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        System.out.println("Total Profit: "+totalDifference);

    }

    public double specificBox(JSONArray boxes, String UID, int i) throws ParseException {

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

        System.out.println("Price you bought at: "+priceOld);
        System.out.println("Price now: "+priceNew);
        System.out.println("Profit: "+difference);

        return difference;
    }

}
