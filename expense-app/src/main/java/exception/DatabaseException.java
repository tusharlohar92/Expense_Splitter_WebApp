package exception;

/**
 * Custom exception for database-related errors such as connection failures,
 * query execution errors, or transaction issues.
 */
public class DatabaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DatabaseException(String message) {
        super("Database error: " + message);
    }

    public DatabaseException(String message, Throwable cause) {
        super("Database error: " + message, cause);
    }
}