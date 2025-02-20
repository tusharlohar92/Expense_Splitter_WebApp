package annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that a string field contains a properly formatted email address.
 * Uses RFC 5322 compliant regex pattern by default.
 */
@Documented // Added to include annotation in Javadoc
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = validation.EmailValidator.class)
public @interface ValidEmail {
    // Can be overridden in messages.properties with key "ValidEmail.message"
    String message() default "Invalid email format";
    
    // Required for validation groups
    Class<?>[] groups() default {};
    
    // Payload for custom metadata (e.g., severity levels)
    Class<? extends Payload>[] payload() default {};
}