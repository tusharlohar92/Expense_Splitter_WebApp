package annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented; // Added import
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that a password meets complexity requirements:
 * - Minimum 8 characters
 * - At least 1 uppercase, 1 lowercase, 1 digit, 1 special character
 */
@Documented // Ensure annotation appears in Javadoc
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = validation.PasswordValidator.class)
public @interface ValidPassword {
    // Customize via messages.properties with key "ValidPassword.message"
    String message() default "Invalid password format";
    
    // Required for validation groups
    Class<?>[] groups() default {};
    
    // Payload for custom metadata
    Class<? extends Payload>[] payload() default {};
}
