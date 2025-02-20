package model;

import annotation.ValidAmount;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents an expense record in the system.
 * Maps to the 'expenses' table in the database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expenses")
public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;  // Required for serialization

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long expenseId;       // Primary key, auto-incremented

    @ValidAmount                  // Custom validation for amount format
    @Positive(message = "Amount must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;    // DECIMAL(10,2) in DB, positive value

    @Column(length = 255)
    private String description;   // Optional expense details (max 255 chars)

    @ManyToOne
    @JoinColumn(name = "group_id")
    private ExpenseGroup group;   // Optional group association

    @ManyToOne
    @JoinColumn(name = "paid_by", nullable = false)
    private User paidBy;          // User who paid the expense (non-null)

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // Auto-generated creation timestamp

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // Auto-updated modification timestamp
}