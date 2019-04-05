package game;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import ext.ws.DBCommoditiesClient;
import ext.ws.DBLocationsClient;
import ext.ws.DBPurchasingClient;
import ext.ws.DBSales;
import ext.ws.DBShips;
import java.util.ArrayList;
import java.util.List;

public class Database {
    
    private static List<Commodity> commodities;
    
    static {
        
        Gson g = new Gson();
        
        commodities = new ArrayList<>();
        String json = new DBCommoditiesClient().findAll_JSON(String.class);
        JsonArray arr = g.fromJson(json, JsonArray.class);
        
        for(int i = 0;i < arr.size();i++) { 
            commodities.add(g.fromJson(arr.get(i), Commodity.class));
        }
        
    }
    
        
                
    private static List<Ship> ships;
    
    static {
        
        ships = new ArrayList<>();
        
        Gson g = new Gson();
        
        JsonArray arr = g.fromJson(
                new DBShips().findAll_JSON(String.class), JsonArray.class);
        
        
        for(int i = 0;i < arr.size();i++) { 
            ships.add(g.fromJson(arr.get(i), Ship.class));
        }
        
    }
    
    
    private static List<Sale> sales;
    
    static {
        
        sales = new ArrayList<>();
        
        Gson g = new Gson();
        
        JsonArray arr = g.fromJson(
                new DBSales().findAll_JSON(String.class), JsonArray.class);
        
        
        for(int i = 0;i < arr.size();i++) { 
            sales.add(g.fromJson(arr.get(i), Sale.class));
        }
        
    }
    
            
    private static List<Purchase> purchases;
    
    static {
        
        purchases = new ArrayList<>();
        
        Gson g = new Gson();
        
        JsonArray arr = g.fromJson(
                new DBPurchasingClient().findAll_JSON(String.class), JsonArray.class);
        
        
        for(int i = 0;i < arr.size();i++) { 
            purchases.add(g.fromJson(arr.get(i), Purchase.class));
        }
        
    }
    
       
        
    private static List<Location> locations;
    
    static {
        
        locations = new ArrayList<>();
        
        Gson g = new Gson();
        
        locations = new ArrayList<>();
        String json = new DBLocationsClient().findAll_JSON(String.class);
        JsonArray arr = g.fromJson(json, JsonArray.class);
        
        for(int i = 0;i < arr.size();i++) { 
            locations.add(g.fromJson(arr.get(i), Location.class));
        }
        
    }
    
    public static List<?> getTable(String table) {
        
        switch(table.toLowerCase()) {
            
            case "commodities":
                return commodities;
            case "locations":
                return locations;
            case "purchasing":
                return purchases;
            case "sales":
                return sales;
            case "ships":
                return ships;
        }
        
        throw new IllegalArgumentException("Unknown table " + table);
    }
    
     public static List<Purchase> getPurchases() {
        return purchases;
    }
     
      public static List<Ship> getShips() {
        return ships;
    }
      
      public static List<Location> getLocations() {
        return locations;
    }
      
        
    public static List<Sale> getSales() {
        return sales;
    }
    
       
    public static List<Commodity> getCommodities() {
        return commodities;
    }
    
}
