package com.csinvestmenttracker;

import java.util.Arrays;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.csinvestmenttracker.wrappers.boxesAccumulated;
import com.csinvestmenttracker.wrappers.singleBox;

public class comparePrices {

    
    fetch f = new fetch();

    public static void main(String[] args) {
        
        comparePrices c = new comparePrices();
        userDatabase u = new userDatabase();

        JSONArray user = u.getUser("1");

        try {
            System.out.println(c.currentBox(user, 0).getPcs());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    /**
     * @param boxes JSONArray with all boxes of a user
     * @return Object that contains all past boxes, all their names, the total prices, the profit and the total pieces
     */
    public boxesAccumulated allPastBoxes(JSONArray boxes) {

        singleBox[] pastBoxes = new singleBox[boxes.size()];
        String[] allNames = new String[boxes.size()];
        double totalPrice = 0;
        double totalSellPrice = 0;
        double totalDifference = 0;
        int totalPcs = 0;

        for(int i = 0; i < boxes.size(); i++){
            singleBox past = pastBox(boxes, i);
            pastBoxes[i] = past;
            allNames[i] = past.getName();
            totalPrice = totalPrice + past.getOldPrice();
            totalSellPrice = totalSellPrice + past.getNewPrice();
            totalDifference = totalDifference + past.getDifference();
            totalPcs = totalPcs + past.getPcs();
        }
        boxesAccumulated ba = new boxesAccumulated(pastBoxes, allNames, totalPrice, totalSellPrice, totalDifference, totalPcs);
        return ba;

    }
    /**
     * @param boxes JSONArray with all boxes of a user
     * @return Object that contains all current boxes, all their names, the total prices, the profit and the total pieces
     */
    public boxesAccumulated allCurrentBoxes(JSONArray boxes) {

        singleBox[] currentBoxes = new singleBox[boxes.size()];
        String[] allNames = new String[boxes.size()];
        double totalPrice = 0;
        double totalSellPrice = 0;
        double totalDifference = 0;
        int totalPcs = 0;

        for(int i = 0; i < boxes.size(); i++) {
            try {
                singleBox current = currentBox(boxes, i);
                currentBoxes[i] = current;
                allNames[i] = current.getName();
                totalPrice = totalPrice + current.getOldPrice();
                totalSellPrice = totalSellPrice + current.getNewPrice();
                totalDifference = totalDifference + current.getDifference();
                totalPcs = totalPcs + current.getPcs();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        boxesAccumulated ba = new boxesAccumulated(currentBoxes, allNames, totalPrice, totalSellPrice, totalDifference, totalPcs);
        return ba;

    }
    /**
     * Gets info on a sold box, that is saved in a JSONArray
     * @param boxes JSONArray with all boxes of a user
     * @param i Which box of the array gets processed
     * @return Object containing Name, price the user bougth the box at, price the user sold the box at and the profit
     */
    public singleBox pastBox(JSONArray boxes, int i) {

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

        singleBox sb = new singleBox(name, price, sellPrice, pcs, difference);

        return sb;
    }

    /**
     * Gets info on a box currently being hold, that is saved in a JSONArray
     * @param boxes JSONArray with all boxes of a user
     * @param i Which box of the array gets processed
     * @return Object containing Name, price the user bougth the box at, current price of the box and the profit at the moment
     */
    public singleBox currentBox(JSONArray boxes, int i) throws ParseException {

        JSONObject box = (JSONObject)boxes.get(i);

        String url = (String)box.get("url");
        url.replace("\\/", "/");
        String[] data = f.all(url);

        int pcs = Integer.valueOf((String)box.get("pcs"));
        double priceOld = Double.valueOf((String)box.get("price")) * pcs;
        double priceNew = Double.valueOf(data[0]) * pcs ;
        double difference = priceNew - priceOld;

        String[] temp = url.split("=");
        String name = temp[3];
        name = name.replace("%20", " ");

        String output = name + ": " + difference + " (" + priceOld + ", " + priceNew + ")";
        System.out.println(output);

        singleBox sb = new singleBox(name, priceOld, priceNew, pcs, difference);

        return sb;
    }

}
