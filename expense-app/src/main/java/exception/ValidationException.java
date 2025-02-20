package exception;

// Custom exception for validation failures (e.g., invalid email format, negative amounts)
public class ValidationException extends RuntimeException {
    // Required for serialization compatibility
    private static final long serialVersionUID = 1L;  

    // Creates exception with custom message
    public ValidationException(String message) {
        super("Validation failed: " + message);  // e.g., "Invalid email format"
    }
}