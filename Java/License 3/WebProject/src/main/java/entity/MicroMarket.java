package entity;


public class MicroMarket {
    
    private String zipCode;
    private Float radius;
    private Float areaLength;
    private Float areaWidth;
    
    // Constructor
    public MicroMarket() {
        this("", 0.0f, 0.0f, 0.0f);
    }
    public MicroMarket(String zipCode, Float radius, Float areaLength, Float areaWidth) {
        this.zipCode = zipCode;
        this.radius = radius;
        this.areaLength = areaLength;
        this.areaWidth = areaWidth;
    }
    
    // Getters && Setters
    public String getZipCode() {
        return zipCode;
    }

    public MicroMarket setZipCode(String zipCode) {
        this.zipCode = zipCode;
        
        return this;
    }

    public Float getRadius() {
        return radius;
    }

    public MicroMarket setRadius(Float radius) {
        this.radius = radius;
        
        return this;
    }

    public Float getAreaLength() {
        return areaLength;
    }

    public MicroMarket setAreaLength(Float areaLength) {
        this.areaLength = areaLength;
        
        return this;
    }

    public Float getAreaWidth() {
        return areaWidth;
    }

    public MicroMarket setAreaWidth(Float areaWidth) {
        this.areaWidth = areaWidth;
        
        return this;
    }
    
}
