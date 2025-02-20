package exception;

import dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler for REST controllers.
 * Provides centralized exception handling and standardized error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles validation errors for @Valid annotated parameters
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        // Extract field-level validation errors
        List<ErrorResponse.FieldErrorDetail> fieldErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> {
                ErrorResponse.FieldErrorDetail detail = new ErrorResponse.FieldErrorDetail();
                detail.setField(error.getField());
                detail.setMessage(error.getDefaultMessage());
                detail.setRejectedValue(error.getRejectedValue());
                return detail;
            })
            .collect(Collectors.toList());

        // Build standardized error response
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError("Validation Error");
        response.setMessage("Invalid request content");
        response.setFieldErrors(fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ResourceNotFoundException (404 Not Found)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
        ResourceNotFoundException ex
    ) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles ConflictException (409 Conflict)
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
        ConflictException ex
    ) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT);
    }

    /**
     * Handles DatabaseException (500 Internal Server Error)
     */
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseError(
        DatabaseException ex
    ) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Builds standardized error response structure
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(
        Exception ex,
        HttpStatus status
    ) {
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status.value());
        response.setError(status.getReasonPhrase());
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, status);
    }
}