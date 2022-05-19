package entity;


public class DiscountCode {
    
    private char code;
    private Float rate;
    
    // Constructor
    public DiscountCode() {
        this('\0', 0.0f);
    }
    public DiscountCode(char code, Float rate) {
        this.code = code;
        this.rate = rate;
    }
    
    // Getters && Setters
    public char getCode() {
        return code;
    }

    public DiscountCode setCode(char code) {
        this.code = code;
        
        return this;
    }

    public Float getRate() {
        return rate;
    }

    public DiscountCode setRate(Float rate) {
        this.rate = rate;
        
        return this;
    }
    
}
