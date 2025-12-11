package life.henrytech.backend.exception;

public class BusinessException extends RuntimeException {
    private int statusCode;
    
    public BusinessException(String message) {
        super(message);
        this.statusCode = 400;
    }
    
    public BusinessException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
}

