package exception;


public class AccessDeniedException extends AbstractException {
    
    public AccessDeniedException(String message) {
        super(message, 401);
    }
    
}
