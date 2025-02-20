package validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import annotation.ValidAmount;
import java.math.BigDecimal;
import java.util.regex.Pattern;

public class AmountValidator implements ConstraintValidator<ValidAmount, BigDecimal> {
    private static final String AMOUNT_REGEX = "^\\d+(\\.\\d{1,2})?$";

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return Pattern.matches(AMOUNT_REGEX, value.toString());
    }
}