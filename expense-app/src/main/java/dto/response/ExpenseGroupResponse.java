package dto.response;

import lombok.Data;
import java.time.LocalDateTime;

// Response DTO for expense groups - maps to 'expense_groups' table
@Data
public class ExpenseGroupResponse {
    private Long groupId;         // Primary key (group_id in DB)
    private String name;          // Group name (not null, max 100 chars)
    
    private Long createdBy;       // User ID of creator (FK to users.user_id)
    
    // Automatic timestamps:
    private LocalDateTime createdAt;  // Group creation time
    private LocalDateTime updatedAt;  // Last modification time
}