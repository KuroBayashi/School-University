package entity;


public class Contact {
        
    private String email;
    private String phone;
    private String fax;
    
    // Constructor
    public Contact() {
        this("", "", "");
    }
    public Contact(String email, String phone, String fax) {
        this.email = email;
        this.phone = phone;
        this.fax = fax;
    }

    // Getters && Setters
    public String getEmail() {
        return email;
    }

    public Contact setEmail(String email) {
        this.email = email;
        
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Contact setPhone(String phone) {
        this.phone = phone;
        
        return this;
    }

    public String getFax() {
        return fax;
    }

    public Contact setFax(String fax) {
        this.fax = fax;
        
        return this;
    }
    
    
}
