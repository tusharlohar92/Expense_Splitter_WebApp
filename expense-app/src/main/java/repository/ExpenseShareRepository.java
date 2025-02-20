package repository;

import model.Expense;
import model.ExpenseShare;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * JPA Repository for expense share operations.
 * Maps to 'expense_shares' table with composite primary key support.
 */
public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, ExpenseShare.ExpenseShareId> {

    // Find all shares for a specific expense (uses fk_share_expense index)
    List<ExpenseShare> findByExpense(Expense expense);
    
    // Delete all shares for an expense (cascades via DB constraint)
    void deleteByExpense(Expense expense);
    
    // Additional useful method
    List<ExpenseShare> findByExpense_ExpenseId(Long expenseId); // Added
}