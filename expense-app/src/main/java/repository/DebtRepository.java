package repository;

import model.Debt;
import model.ExpenseGroup;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for debt management operations.
 * Provides custom queries for debt tracking and settlement.
 */
@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {

    // Core debt relationship queries
    Optional<Debt> findByOwedByAndOwedTo(User owedBy, User owedTo);
    Optional<Debt> findByOwedByAndOwedToAndGroup(User owedBy, User owedTo, ExpenseGroup group);
    
    // User-centric debt queries
    List<Debt> findByOwedBy(User owedBy);
    List<Debt> findByOwedTo(User owedTo);
    List<Debt> findByOwedByAndGroup(User owedBy, ExpenseGroup group);
    List<Debt> findByOwedToAndGroup(User owedTo, ExpenseGroup group);
    
    // Group debt management
    List<Debt> findByGroup(ExpenseGroup group);
    List<Debt> findByGroupAndIsSettled(ExpenseGroup group, boolean isSettled);
    
    // Settlement status queries
    List<Debt> findByIsSettled(boolean isSettled);
    List<Debt> findByIsSettledAndGroup(boolean isSettled, ExpenseGroup group);
}