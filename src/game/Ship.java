package game;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import ext.ws.DBShips;
import java.util.ArrayList;
import java.util.List;


public class Ship {

    
   
    
    private final int id;
    private final int capacity;
    private final int fuel;
    private final String name;
    private final String size;
    private final float speed;

    public Ship(int id, int capacity, int fuel, String name, String size, float speed) {
        this.id = id;
        this.capacity = capacity;
        this.fuel = fuel;
        this.name = name;
        this.size = size;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFuel() {
        return fuel;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public float getSpeed() {
        return speed;
    }
    
        
    @Override
    public String toString() {
        return name;
    }
    
}
