package entity;


public class ProductCode {
    
    private String code;
    private DiscountCode discountCode;
    private String description;
    
    // Constructor
    public ProductCode() {
        this("", new DiscountCode(), "");
    }
    public ProductCode(String code, DiscountCode discountCode, String description) {
        this.code = code;
        this.discountCode = discountCode;
        this.description = description;
    }
    
    // Getters && Setters
    public String getCode() {
        return code;
    }

    public ProductCode setCode(String code) {
        this.code = code;
        
        return this;
    }

    public DiscountCode getDiscountCode() {
        return discountCode;
    }

    public ProductCode setDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
        
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductCode setDescription(String description) {
        this.description = description;
        
        return this;
    }
}
