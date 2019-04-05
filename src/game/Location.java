package game;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import ext.ws.DBCommoditiesClient;
import ext.ws.DBLocationsClient;
import java.util.ArrayList;
import java.util.List;


public class Location {
 
    
    
    
    private final int id;
    private final String name;
    private final float x, y, z;
    private final String maxSize;

    public Location(int id, String name, float x, float y, float z, String maxSize) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.maxSize = maxSize;
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public String getMaxSize() {
        return maxSize;
    }
    
        
    @Override
    public String toString() {
        return name;
    }
    
    
}
