package entity;


public class Manufacturer {
    
    private Integer id;
    private String name;
    private Location location;
    private Contact contact;
    private String rep;
    
    // Constructor
    public Manufacturer() {
        this(-1, "", new Location(), new Contact(), "");
    }
    public Manufacturer(Integer id, String name, Location location, Contact contact, String rep) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.contact = contact;
        this.rep = rep;
    }
    
    // Getters && Setters

    public Integer getId() {
        return id;
    }

    public Manufacturer setId(Integer id) {
        this.id = id;
        
        return this;
    }

    public String getName() {
        return name;
    }

    public Manufacturer setName(String name) {
        this.name = name;
        
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public Manufacturer setLocation(Location location) {
        this.location = location;
        
        return this;
    }

    public Contact getContact() {
        return contact;
    }

    public Manufacturer setContact(Contact contact) {
        this.contact = contact;
        
        return this;
    }

    public String getRep() {
        return rep;
    }

    public Manufacturer setRep(String rep) {
        this.rep = rep;
        
        return this;
    }
    
}
