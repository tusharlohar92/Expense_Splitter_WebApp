package dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Standard error response structure for API exceptions.
 * Contains timestamp, HTTP status, error type, and detailed messages.
 */
@Data
public class ErrorResponse {
    private LocalDateTime timestamp; // When the error occurred
    private int status;              // HTTP status code
    private String error;           // Error category (e.g., "Validation Error")
    private String message;         // Human-readable error description
    private List<FieldErrorDetail> fieldErrors; // Validation errors (optional)

    /**
     * Nested class for field-level validation errors.
     */
    @Data
    public static class FieldErrorDetail {
        private String field;       // Name of the invalid field
        private String message;     // Error message for the field
        private Object rejectedValue; // Invalid value provided
    }
}
