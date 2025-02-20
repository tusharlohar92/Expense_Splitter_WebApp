package dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// Response DTO for debt records - matches 'debts' table structure
@Data
public class DebtResponse {
    private Long debtId;          // Maps to debt_id (primary key)
    
    private BigDecimal amount;    // Decimal(10,2) from DB - positive value
    
    private Long owedBy;          // References users.user_id (debtor)
    private Long owedTo;          // References users.user_id (creditor)
    
    private Long groupId;         // Optional FK to expense_groups.group_id
    private Boolean isSettled;    // Maps to is_settled (0=false, 1=true)
    
    // Automatic timestamps from DB:
    private LocalDateTime createdAt;  // Initial creation time
    private LocalDateTime updatedAt;  // Last modification time
}