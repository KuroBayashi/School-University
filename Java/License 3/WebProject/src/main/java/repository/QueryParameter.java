package repository;


public class QueryParameter {
    
    private final String field;
    private final String type;
    private final Object value;
    
    // Constructor
    public QueryParameter(String field, Object value) {
        this(field, "=", value);
    }
    public QueryParameter(String field, String type, Object value) {
        
        this.field = field;
        this.type = type;
        this.value = value;
    }
    
    // Getters
    public String getField() {
        
        return this.field;
    }
    
    public String getType() {
        
        return this.type;
    }
    
    public Object getValue() {
        
        return this.value;
    }
}
