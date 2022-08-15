package com.csinvestmenttracker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.server.UID;

import javax.json.JsonObject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class userDatabase {

    public userDatabase() {



    }

    public static void main(String[] args) {

        userDatabase u = new userDatabase();
        
        try {
            u.load();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

    }

    public JSONArray create() {

        JSONArray users = new JSONArray();
        JSONObject user = new JSONObject();

        JSONArray boxes = new JSONArray();
        JSONObject box = new JSONObject();

        user.put("UID", "1");

        box.put("url", "https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Gamma%202%20Case");
        box.put("pcs", "500");
        box.put("price", "0.03");

        boxes.add(box);

        user.put("cases", boxes);

        users.add(user);

        return users;

    }

    public JSONArray load() throws ParseException, FileNotFoundException, IOException {
        
        JSONParser parser = new JSONParser();
        JSONArray parsed = (JSONArray)parser.parse(new FileReader("users.json"));

        System.out.println(parsed);

        return parsed;

    }

    public void save(JSONArray toSave) {

        try {
            FileWriter fw = new FileWriter("users.json");
            fw.write(toSave.toJSONString());
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
