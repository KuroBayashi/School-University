package exception;


public class RepositoryException extends AbstractException {
    
    public RepositoryException(String message) {
        super(message, 500);
    }
    
}
