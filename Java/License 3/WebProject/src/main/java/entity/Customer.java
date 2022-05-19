package entity;


public class Customer {

    private Integer id;
    private String name;
    private DiscountCode discountCode;
    private Integer credit;
    private Location location;
    private Contact contact;

    // Constructor
    public Customer() {
        this(-1, "", new DiscountCode(), 0, new Location(), new Contact());
    }
    
    public Customer(Integer id, String name, DiscountCode discountCode, Integer credit, Location location, Contact contact) {
        this.id = id;

        this.name = name;
        this.discountCode = discountCode;
        this.credit = credit;

        this.location = location;
        this.contact = contact;
    }

    // Getters && Setters
    public Integer getId() {
        return id;
    }

    public Customer setId(Integer id) {
        this.id = id;
        
        return this;
    }

    public String getName() {
        return name;
    }

    public Customer setName(String name) {
        this.name = name;
        
        return this;
    }

    public DiscountCode getDiscountCode() {
        return discountCode;
    }

    public Customer setDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
        
        return this;
    }

    public Integer getCredit() {
        return credit;
    }

    public Customer setCredit(Integer credit) {
        this.credit = credit;
        
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public Customer setLocation(Location location) {
        this.location = location;
        
        return this;
    }

    public Contact getContact() {
        return contact;
    }

    public Customer setContact(Contact contact) {
        this.contact = contact;
        
        return this;
    }

}
