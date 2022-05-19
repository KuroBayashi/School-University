package exception;


public abstract class AbstractException extends Exception {
    
    protected Integer code;
    
    public AbstractException(String message) {
        this(message, 0);
    }
    
    public AbstractException(String message, Integer code) {
        super(message);
        this.code = code;
    }
    
    public Integer getCode() {
        return this.code;
    }
}
