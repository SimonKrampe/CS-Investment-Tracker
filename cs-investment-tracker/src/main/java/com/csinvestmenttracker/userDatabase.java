package com.csinvestmenttracker;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class userDatabase {

    public static void main(String[] args) {

        userDatabase u = new userDatabase();
        
        //u.addUser("1");
        //u.addBox("1", "https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Gamma%202%20Case", "500", "0.03");
        //u.removeBox("1", "https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Gamma%202%20Case", "0.7");

    }

    public void removeFromSold(String UID, String url) {

        JSONObject users = load("pastTrades.json");
        if(users == null){
            System.out.println("Error with parsing");
            return;
        }

        JSONObject box = new JSONObject();

        JSONArray boxes = (JSONArray)users.get(UID);
        for(int i = 0; i < boxes.size(); i++) {

            box = (JSONObject)boxes.get(i);

            if(box.get("url") == url) {

                boxes.remove(i);
                i = boxes.size();

            }

        }

        save(users, "pastTrades.json");

    }

    public void removeBox(String UID, String url, String sellPrice) {

        JSONObject users = load("users.json");
        if(users == null){
            System.out.println("Error with parsing");
            return;
        }

        JSONObject box = new JSONObject();

        JSONArray boxes = (JSONArray)users.get(UID);
        for(int i = 0; i < boxes.size(); i++) {

            box = (JSONObject)boxes.get(i);

            if(box.get("url") == url) {

                boxes.remove(i);
                i = boxes.size();

            }

        }
        JSONObject pastTrades = load("pastTrades.json");
        JSONArray oldBoxes = (JSONArray)pastTrades.get(UID);

        box.put("sellPrice", sellPrice);
        oldBoxes.add(box);

        save(pastTrades, "pastTrades.json");
        save(users, "users.json");

    }

    public JSONArray getUser(String UID) {
        
        JSONObject users = load("users.json");
        if(users == null){
            System.out.println("Error with parsing");
            return null;
        }

        JSONArray boxes = (JSONArray)users.get(UID);
        return boxes;
    }

    public JSONArray getUserPast(String UID) {

        JSONObject users = load("pastTrades.json");
        if(users == null){
            System.out.println("Error with parsing");
            return null;
        }

        JSONArray boxes = (JSONArray)users.get(UID);
        return boxes;

    }

    public JSONObject create(String UID) {

        JSONObject users = new JSONObject();
        JSONObject box = new JSONObject();

        JSONArray boxes = new JSONArray();

        box.put("url", "https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Prisma%202%20Case");
        box.put("pcs", "1000");
        box.put("price", "0.03");

        boxes.add(box);

        users.put(UID, boxes);

        return users;

    }

    public void addUser(String UID) {

        JSONObject users = load("users.json");
        if(users == null){
            System.out.println("Error with parsing");
            return;
        }

        JSONArray boxes = new JSONArray();

        users.put(UID, boxes);

        save(users, "users.json");
        save(users, "pastTrades.json");

    }

    public void addBox(String UID, String url, String pcs, String price) {

        JSONObject users = load("users.json");
        if(users == null){
            System.out.println("Error with parsing");
            return;
        }

        JSONObject box = new JSONObject();
        box.put("url", url);
        box.put("pcs", pcs);
        box.put("price", price);

        JSONArray boxes = (JSONArray)users.get(UID);

        boxes.add(box);

        save(users, "users.json");
    }

    public JSONObject load(String fileName) {
        
        JSONParser parser = new JSONParser();
        JSONObject parsed;
        try {
            parsed = (JSONObject)parser.parse(new FileReader(fileName));

            return parsed;

        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void save(JSONObject toSave, String fileName) {

        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(toSave.toJSONString());
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
