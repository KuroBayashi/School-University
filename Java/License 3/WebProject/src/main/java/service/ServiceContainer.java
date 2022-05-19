package service;

import java.util.HashMap;
import java.util.Map;


public class ServiceContainer {
    
    private Map<String, AbstractService> services = new HashMap<>();
    
    public ServiceContainer() {
        
    }
    
    public FlashBag getFlashBag(){
        if (!this.services.containsKey("flashBag"))
            this.services.put("flashBag", new FlashBag());
        
        return (FlashBag) this.services.get("flashBag");
    }
    
}
