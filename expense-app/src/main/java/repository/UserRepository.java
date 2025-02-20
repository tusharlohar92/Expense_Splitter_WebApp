package repository;

import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for user management operations.
 * Maps to 'users' table with email uniqueness enforcement.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (uses unique email index)
    Optional<User> findByEmail(String email);
    
    // Check email existence (uses unique email index)
    boolean existsByEmail(String email);
    
    // Additional suggested method
    List<User> findByNameContainingIgnoreCase(String name); // Added for search
}