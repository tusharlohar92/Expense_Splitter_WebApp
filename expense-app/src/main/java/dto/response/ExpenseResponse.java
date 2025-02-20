package dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Positive;

// Response DTO for expenses - maps to 'expenses' table
@Data
public class ExpenseResponse {
    private Long expenseId;       // Primary key (expense_id in DB)
    
    @Positive
    private BigDecimal amount;    // DECIMAL(10,2) from DB - positive value
    
    private String description;   // Optional details (max 255 chars)
    
    private Long groupId;         // Optional FK to expense_groups.group_id
    private Long paidBy;          // Required FK to users.user_id (payer)
    
    // Automatic timestamps:
    private LocalDateTime createdAt;  // Initial creation time
    private LocalDateTime updatedAt;  // Last modification time (auto-updated)
}