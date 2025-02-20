package dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Request DTO for creating/updating an expense.
 */
@Data
public class ExpenseRequest {
    @NotNull @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private String description;

    private Long groupId; // Optional group ID

    @NotNull(message = "Payer ID is required")
    private Long paidBy; // User ID of the payer

    @NotNull(message = "Shares allocation is required")
    private Map<Long, BigDecimal> shares; // User ID to share amount
}
