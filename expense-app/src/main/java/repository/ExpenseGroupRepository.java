package repository;

import model.ExpenseGroup;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * JPA Repository for expense group operations.
 * Maps to 'expense_groups' table with group management capabilities.
 */
public interface ExpenseGroupRepository extends JpaRepository<ExpenseGroup, Long> {

    // Find groups created by a specific user (uses index on created_by)
    List<ExpenseGroup> findByCreatedBy(User createdBy);
    
    // Search groups by name (case-insensitive, no index - consider adding)
    List<ExpenseGroup> findByNameContainingIgnoreCase(String name);
    
    // Check group name existence (recommend adding unique constraint)
    boolean existsByName(String name);
}