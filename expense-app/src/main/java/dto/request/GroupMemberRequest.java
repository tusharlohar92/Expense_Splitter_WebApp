package dto.request;

import lombok.Data;

/**
 * Request DTO for adding a user to a group.
 */
@Data
public class GroupMemberRequest {
    private Long groupId;
    private Long userId;
}
