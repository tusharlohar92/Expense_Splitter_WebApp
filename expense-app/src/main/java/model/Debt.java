package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a debt between two users, optionally linked to an expense group.
 * Maps to the 'debts' table in the database.
 */
@Data
@Entity
@Table(name = "debts")
public class Debt implements Serializable {
    private static final long serialVersionUID = 1L;  // Required for serialization

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debt_id")
    private Long debtId;          // Primary key, auto-generated

    @Positive(message = "Debt amount must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;    // DECIMAL(10,2) in DB, positive value

    @ManyToOne
    @JoinColumn(name = "owed_by", nullable = false)
    private User owedBy;          // User who owes money (maps to users.user_id)

    @ManyToOne
    @JoinColumn(name = "owed_to", nullable = false)
    private User owedTo;          // User owed to (maps to users.user_id)

    @ManyToOne
    @JoinColumn(name = "group_id")
    private ExpenseGroup group;   // Optional group association

    @Column(name = "is_settled", columnDefinition = "TINYINT(1) default 0")
    private Boolean isSettled = false;  // Settlement status (0=false, 1=true)

    @Column(name = "settled_at")
    private LocalDateTime settledAt;     // Manual settlement timestamp

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;     // Auto-generated creation time

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;     // Auto-updated on changes
}