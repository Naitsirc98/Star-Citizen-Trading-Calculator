package game;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import ext.ws.DBCommoditiesClient;
import ext.ws.DBPurchasingClient;
import java.util.ArrayList;
import java.util.List;

public class Purchase {
    

    
   
    
    private final int id;
    private final String commodity;
    private final String location;
    private final float price;

    public Purchase(int id, String commodity, String location, float price) {
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
