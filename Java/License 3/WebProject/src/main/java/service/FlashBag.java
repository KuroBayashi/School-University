package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FlashBag extends AbstractService {
    
    private Map<String, ArrayList<String>> bag = new HashMap<>();
    
    // Methods
    public void add(String type, String message) {
        if (!this.bag.containsKey(type))
            this.bag.put(type, new ArrayList<String>());
                    
        this.bag.get(type).add(message);
    }
    
    public Map<String, ArrayList<String>> getAll() {
        return this.bag;
    }
    
    public ArrayList<String> get(String type) {
        if (this.bag.containsKey(type))
            return this.bag.get(type);
        
        return null;
    }
    
    public void clear() {
        this.bag.clear();
    }
}
