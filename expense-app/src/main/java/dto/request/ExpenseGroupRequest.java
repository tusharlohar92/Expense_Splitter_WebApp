package dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request DTO for creating or updating an expense group.
 * Validates that the group name is provided.
 */
@Data
public class ExpenseGroupRequest {
    @NotBlank(message = "Group name cannot be blank")
    private String name;

    private Long createdBy; // User ID of the group creator
}
