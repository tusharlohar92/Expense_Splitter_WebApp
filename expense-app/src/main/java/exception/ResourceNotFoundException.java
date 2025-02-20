package exception;

/**
 * Exception thrown when a requested resource is not found in the system.
 * Maps to HTTP 404 Not Found responses.
 */
public class ResourceNotFoundException extends RuntimeException {
    // Required for serialization/deserialization compatibility
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with detailed message
     * @param resourceName Name of the missing resource (e.g., "User", "Expense")
     * @param fieldName    Field used for lookup (e.g., "id", "email")
     * @param fieldValue   Value that failed to find resource (e.g., 123, "user@example.com")
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", 
              resourceName,  // e.g., "ExpenseGroup"
              fieldName,    // e.g., "groupId"
              fieldValue)); // e.g., 456
    }
}