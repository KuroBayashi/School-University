package entity;

import java.sql.Date;


public class PurchaseOrder {
    
    private Integer num;
    private Customer customer;
    private Product product;
    private Integer quantity;
    private Float shippingCost;
    private Date salesDate;
    private Date shippingDate;
    private String freightCompany;
    
    // Constructor
    public PurchaseOrder() {
        this(-1, new Customer(), new Product(), 0, 0.0f, null, null, "");
    }
    public PurchaseOrder(Integer num, Customer customer, Product product, Integer quantity, Float shippingCost, Date salesDate, Date shippingDate, String freightCompany) {
        this.num = num;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.shippingCost = shippingCost;
        this.salesDate = salesDate;
        this.shippingDate = shippingDate;
        this.freightCompany = freightCompany;
    }
    
    // Getters && Setters
    public Integer getNum() {
        return num;
    }

    public PurchaseOrder setNum(Integer num) {
        this.num = num;
        
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public PurchaseOrder setCustomer(Customer customer) {
        this.customer = customer;
        
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public PurchaseOrder setProduct(Product product) {
        this.product = product;
        
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PurchaseOrder setQuantity(Integer quantity) {
        this.quantity = quantity;
        
        return this;
    }

    public Float getShippingCost() {
        return shippingCost;
    }

    public PurchaseOrder setShippingCost(Float shippingCost) {
        this.shippingCost = shippingCost;
        
        return this;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public PurchaseOrder setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
        
        return this;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public PurchaseOrder setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
        
        return this;
    }

    public String getFreightCompany() {
        return freightCompany;
    }

    public PurchaseOrder setFreightCompany(String freightCompany) {
        this.freightCompany = freightCompany;
        
        return this;
    }
    
}
