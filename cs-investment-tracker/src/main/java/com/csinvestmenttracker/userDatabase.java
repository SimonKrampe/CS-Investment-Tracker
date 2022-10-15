package com.csinvestmenttracker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.server.UID;

import javax.json.JsonObject;
import javax.servlet.jsp.tagext.BodyContent;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class userDatabase {

    public static void main(String[] args) {

        userDatabase u = new userDatabase();
        
        u.addBox("1", "https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Danger%20Zone%20Case", "500", "0.10");

    }

    public JSONArray getUser(String UID) {
        
        JSONObject users = load();
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

        box.put("url", "https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Gamma%202%20Case");
        box.put("pcs", "500");
        box.put("price", "0.03");

        boxes.add(box);

        users.put(UID, boxes);

        return users;

    }

    public void addUser(String UID) {

        JSONObject users = load();
        if(users == null){
            System.out.println("Error with parsing");
            return;
        }

        JSONArray boxes = new JSONArray();

        users.put(UID, boxes);

        save(users);

    }

    public void addBox(String UID, String url, String pcs, String price) {

        JSONObject users = load();
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

        save(users);
    }

    public JSONObject load() {
        
        JSONParser parser = new JSONParser();
        JSONObject parsed;
        try {
            parsed = (JSONObject)parser.parse(new FileReader("users.json"));

            System.out.println(parsed);

            return parsed;

        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void save(JSONObject toSave) {

        try {
            FileWriter fw = new FileWriter("users.json");
            fw.write(toSave.toJSONString());
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
