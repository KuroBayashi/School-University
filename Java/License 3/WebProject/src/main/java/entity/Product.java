package entity;


public class Product {
    
    private Integer id;
    private Manufacturer manufacturer;
    private ProductCode code;
    private Float purchaseCost;
    private Integer quantity;
    private Float markup;
    private Boolean available;
    private String description;
    
    // Constructor
    public Product() {
        this(-1, new Manufacturer(), new ProductCode(), 0.0f, 0, 0.0f, false, "");
    }
    public Product(Integer id, Manufacturer manufacturer, ProductCode code, Float purchaseCost, Integer quantity, Float markup, Boolean available, String description) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.code = code;
        this.purchaseCost = purchaseCost;
        this.quantity = quantity;
        this.markup = markup;
        this.available = available;
        this.description = description;
    }
    
    // Getters && Setters
    public Integer getId() {
        return id;
    }

    public Product setId(Integer id) {
        this.id = id;
        
        return this;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public Product setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        
        return this;
    }

    public ProductCode getCode() {
        return code;
    }

    public Product setCode(ProductCode code) {
        this.code = code;
        
        return this;
    }

    public Float getPurchaseCost() {
        return purchaseCost;
    }

    public Product setPurchaseCost(Float purchaseCost) {
        this.purchaseCost = purchaseCost;
        
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product setQuantity(Integer quantity) {
        this.quantity = quantity;
        
        return this;
    }

    public Float getMarkup() {
        return markup;
    }

    public Product setMarkup(Float markup) {
        this.markup = markup;
        
        return this;
    }

    public Boolean getAvailable() {
        return available;
    }

    public Product setAvailable(Boolean available) {
        this.available = available;
        
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        
        return this;
    }
    
}
