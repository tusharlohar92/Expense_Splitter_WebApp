package model;

import annotation.ValidEmail;
import annotation.ValidPassword;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user account in the system.
 * Maps to the 'users' table in the database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;  // Required for serialization

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;          // Primary key, auto-incremented

    @ValidEmail                   // Custom email format validation
    @Column(nullable = false, unique = true, length = 255)
    private String email;         // Unique email (max 255 chars)

    @ValidPassword                // Custom password complexity validation
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;  // Hashed password (never store plain text)

    @Column(nullable = false, length = 100)
    private String name;          // User's full name (max 100 chars)

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // Account creation timestamp

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // Last profile update timestamp

    // Relationships
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpenseGroup> createdGroups = new ArrayList<>();  // Groups created by user

    @OneToMany(mappedBy = "paidBy", cascade = CascadeType.ALL)
    private List<Expense> expenses = new ArrayList<>();  // Expenses paid by user

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<GroupMember> groupMemberships = new ArrayList<>();  // Group participations
}