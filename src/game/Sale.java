package game;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import ext.ws.DBPurchasingClient;
import ext.ws.DBSales;
import java.util.ArrayList;
import java.util.List;


public class Sale {
    
            

  
    
    private final int id;
    private final String commodity;
    private final String location;
    private final float price;

    public Sale(int id, String commodity, String location, float price) {
        this.id = id;
        this.commodity = commodity;
        this.location = location;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getCommodity() {
        return commodity;
    }

    public String getLocation() {
        return location;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return commodity;
    }
    
}
