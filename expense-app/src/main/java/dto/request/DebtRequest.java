package dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

/**
 * Request DTO for creating or updating a debt record.
 * Validates that the amount is positive and required fields are present.
 */
@Data
public class DebtRequest {
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be a positive value")
    private BigDecimal amount;

    @NotNull(message = "Debtor ID cannot be null")
    private Long owedBy; // User ID of the debtor

    @NotNull(message = "Creditor ID cannot be null")
    private Long owedTo; // User ID of the creditor

    private Long groupId; // Optional group association
}
