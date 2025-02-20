package dto.response;

import lombok.Data;
import java.time.LocalDateTime;

// Response DTO for group memberships - maps to 'group_members' table
@Data
public class GroupMemberResponse {
    private Long groupId;      // Composite PK part - references expense_groups.group_id
    private Long userId;       // Composite PK part - references users.user_id
    private LocalDateTime joinedAt;  // Membership timestamp (auto-generated on creation)
}