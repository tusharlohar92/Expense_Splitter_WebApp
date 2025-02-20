package model;

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
 * Represents a group of users sharing expenses.
 * Maps to the 'expense_groups' table in the database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expense_groups")
public class ExpenseGroup implements Serializable {
    private static final long serialVersionUID = 1L;  // Required for serialization

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;         // Primary key, auto-generated

    @Column(nullable = false, length = 100)
    private String name;           // Group name (max 100 characters)

    @ManyToOne
    @JoinColumn(name = "created_by")  // Matches DB's DEFAULT NULL
    private User createdBy;        // Optional group creator

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // Auto-generated creation time

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // Auto-updated on changes

    // Relationships
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Expense> expenses = new ArrayList<>();  // All group expenses

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupMember> members = new ArrayList<>();  // Group participants
}