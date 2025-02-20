package dto.response;

import lombok.Data;
import java.time.LocalDateTime;

// Response DTO for user data - maps to 'users' table
@Data
public class UserResponse {
    private Long userId;        // Primary key (user_id in DB)
    private String email;       // Unique email address (matches users.email UNIQUE constraint)
    private String name;        // User's full name (max 100 characters)
    
    private LocalDateTime createdAt;  // Account creation timestamp (auto-generated)
}