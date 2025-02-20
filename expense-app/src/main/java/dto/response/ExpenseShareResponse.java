package dto.response;

import lombok.Data;
import java.math.BigDecimal;

// Response DTO for expense shares - maps to 'expense_shares' table
@Data
public class ExpenseShareResponse {
    private Long expenseId;  // Composite PK part - references expenses.expense_id
    private Long userId;     // Composite PK part - references users.user_id
    private BigDecimal share; // Positive decimal value (matches DECIMAL(10,2) in DB)
}