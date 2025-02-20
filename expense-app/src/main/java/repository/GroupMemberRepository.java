package repository;

import model.ExpenseGroup;
import model.GroupMember;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * JPA Repository for group membership management.
 * Maps to 'group_members' table with composite primary key support.
 */
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMember.GroupMemberId> {

    // Check membership existence (uses idx_group_member index)
    boolean existsByGroupAndUser(ExpenseGroup group, User user);
    
    // Find all members of a group (uses fk_member_group index)
    List<GroupMember> findByGroup(ExpenseGroup group);
    
    // Find all groups for a user (uses fk_member_user index)
    List<GroupMember> findByUser(User user);
    
    // Delete all group members (cascades via DB constraints)
    void deleteByGroup(ExpenseGroup group);
}