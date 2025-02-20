package annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation annotation for monetary amounts.
 * Validates that a field matches the specified amount format.
 */
@Documented
@Constraint(validatedBy = validation.AmountValidator.class)  // Links to validator implementation
@Target({ElementType.FIELD})  // Applicable to class fields
@Retention(RetentionPolicy.RUNTIME)  // Available at runtime via reflection
public @interface ValidAmount {
    // Error message template (can be overridden in properties files)
    String message() default "Invalid amount format (valid: 10 or 10.99)";
    
    // Required for constraint grouping
    Class<?>[] groups() default {};
    
    // Payload for custom metadata
    Class<? extends Payload>[] payload() default {};
}