package entity;


public class Location {
    
    private String addressLine_1;
    private String addressLine_2;
    private MicroMarket microMarket;
    private String city;
    private String state;
    
    // Constructor
    public Location() {
        this("", "", new MicroMarket(), "", "");
    }
    public Location(String addressLine_1, String addressLine_2, MicroMarket microMarket, String city, String state) {
        this.addressLine_1 = addressLine_1;
        this.addressLine_2 = addressLine_2;
        this.microMarket = microMarket;
        this.city = city;
        this.state = state;
    }
    
    // Getters && Setters
    public String getAddressLine_1() {
        return addressLine_1;
    }

    public Location setAddressLine_1(String addressLine_1) {
        this.addressLine_1 = addressLine_1;
        
        return this;
    }

    public String getAddressLine_2() {
        return addressLine_2;
    }

    public Location setAddressLine_2(String addressLine_2) {
        this.addressLine_2 = addressLine_2;
        
        return this;
    }

    public MicroMarket getMicroMarket() {
        return microMarket;
    }

    public Location setMicroMarket(MicroMarket microMarket) {
        this.microMarket = microMarket;
        
        return this;
    }

    public String getCity() {
        return city;
    }

    public Location setCity(String city) {
        this.city = city;
        
        return this;
    }

    public String getState() {
        return state;
    }

    public Location setState(String state) {
        this.state = state;
        
        return this;
    }
    
}
