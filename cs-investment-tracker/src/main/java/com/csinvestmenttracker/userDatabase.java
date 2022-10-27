package com.csinvestmenttracker;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class keeps track of all users. It is capable of creating new users, adding new boxes to them and removing them.
 * It also saves a JSON with all user data and can load it aswell.
 */
public class userDatabase {

    public static void main(String[] args) {

        userDatabase u = new userDatabase();
        
        //u.addUser("1");
        //u.addBox("1", "https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Gamma%202%20Case", "500", "0.03");
        //u.removeBox("1", "https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=Gamma%202%20Case", "0.7");

    }
    /**
     * Completely removes a box that has been moved to the file with past trades (sold) 
     * @param url URL of the box to remove
     */
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
    /**
     * Removes a box from a user and saves it along with the sell price in another file. 
     * @param url URL of the box to remove
     * @param sellPrice Price the boxes were sold at
     */
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
    /**
     * Loads a user from the regular database. 
     * @return The array with all boxes of specific user. 
     */
    public JSONArray getUser(String UID) {
        
        JSONObject users = load("users.json");
        if(users == null){
            System.out.println("Error with parsing");
            return null;
        }

        JSONArray boxes = (JSONArray)users.get(UID);
        return boxes;
    }
    /**
     * Loads a user from the database containing sold boxes. 
     * @return The array with all boxes of specific user. 
     */
    public JSONArray getUserPast(String UID) {

        JSONObject users = load("pastTrades.json");
        if(users == null){
            System.out.println("Error with parsing");
            return null;
        }

        JSONArray boxes = (JSONArray)users.get(UID);
        return boxes;

    }
    /**Creates a test user with a box along with an empty database. ONLY TEST PURPOSES! */
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
    /**
     * Adds a user to the regular database and the one for past trades.
     * @param UID Unique ID of the user
     */
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
    /**
     * Adds a box to a given user 
     * @param UID Unique ID of the user
     * @param url Hash url of the box to add
     * @param pcs How many boxes were bought
     * @param price At wich price the boxes were bougth
     */
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
    /**
     * Loads the given JSON File.
     * @param fileName Name of the file to load. ("users.json", "pastTrades.json")
     * @return Returns JSONObject
     */
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
    /**
     * Saves a JSONObject as a JSON File with the given Name 
     * @param toSave JSONObject to save
     * @param fileName Name the JSONObject will be saved as. ("users.json", "pastTrades.json")
     */
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
