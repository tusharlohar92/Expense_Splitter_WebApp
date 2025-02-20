package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a user's membership in an expense group.
 * Uses a composite primary key (groupId + userId).
 * Maps to the 'group_members' table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_members")
public class GroupMember implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private GroupMemberId id;     // Composite key (groupId + userId)

    @ManyToOne
    @MapsId("groupId")           // Links to composite key part
    @JoinColumn(name = "group_id")
    private ExpenseGroup group;   // Associated expense group

    @ManyToOne
    @MapsId("userId")            // Links to composite key part
    @JoinColumn(name = "user_id")
    private User user;            // Group member user

    @CreationTimestamp
    @Column(name = "joined_at", updatable = false)
    private LocalDateTime joinedAt;  // Auto-generated membership timestamp

    /**
     * Embedded composite key class for group memberships
     */
    @Data
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupMemberId implements Serializable {
        private static final long serialVersionUID = 1L;
        
        @Column(name = "group_id")
        private Long groupId;     // Part of composite key
        
        @Column(name = "user_id")
        private Long userId;      // Part of composite key
    }
}