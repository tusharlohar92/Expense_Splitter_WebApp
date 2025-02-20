package repository;

import model.Expense;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * JPA Repository for expense operations.
 * Maps to 'expenses' table with expense tracking capabilities.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Find expenses by payer's user ID (uses idx_expenses_paid_by index)
    List<Expense> findByPaidBy_UserId(Long userId);
    
    // Find expenses by group ID (uses idx_expenses_group index)
    List<Expense> findByGroupGroupId(Long groupId);
    
    // Find expenses by payer entity
    List<Expense> findByPaidBy(User paidBy);
}