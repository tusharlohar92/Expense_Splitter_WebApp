package exception;

// Custom exception for resource conflict scenarios (e.g., duplicate emails)
public class ConflictException extends RuntimeException {
    // Required for serialization/deserialization
    private static final long serialVersionUID = 1L;  

    // Basic constructor with custom message
    public ConflictException(String message) {
        super(message);  // e.g., "Email already registered"
    }

    // Detailed constructor for resource conflicts
    public ConflictException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s: '%s'", 
              resourceName,      // e.g., "User"
              fieldName,        // e.g., "email"
              fieldValue));     // e.g., "user@example.com"
    }
}