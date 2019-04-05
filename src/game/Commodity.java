package game;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import ext.ws.DBCommoditiesClient;
import java.util.ArrayList;
import java.util.List;

public class Commodity {
    

 
    
    private final int id;
    private final String name;
    private final String category;

    public Commodity(int id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
    
        
    @Override
    public String toString() {
        return name;
    }

}
