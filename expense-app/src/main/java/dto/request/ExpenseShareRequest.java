package dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

/**
 * Request DTO for creating/updating an expense share.
 */
@Data
public class ExpenseShareRequest {
    @NotNull(message = "Expense ID is required")
    private Long expenseId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull @Positive(message = "Share amount must be positive")
    private BigDecimal share;
}
