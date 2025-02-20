package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Represents the share of an expense allocated to a specific user.
 * Uses a composite primary key (expenseId + userId).
 * Maps to the 'expense_shares' table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expense_shares")
public class ExpenseShare implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ExpenseShareId id;    // Composite key (expenseId + userId)

    @ManyToOne
    @MapsId("expenseId")          // Links to composite key part
    @JoinColumn(name = "expense_id")
    private Expense expense;      // Associated expense record

    @ManyToOne
    @MapsId("userId")             // Links to composite key part
    @JoinColumn(name = "user_id")
    private User user;            // User who owes this share

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal share;     // Share amount (positive value)

    /**
     * Embedded composite key class for expense shares
     */
    @Data
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExpenseShareId implements Serializable {
        private static final long serialVersionUID = 1L;
        
        @Column(name = "expense_id")
        private Long expenseId;   // Part of composite key
        
        @Column(name = "user_id")
        private Long userId;      // Part of composite key
    }
}