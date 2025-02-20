package service;

import dto.request.ExpenseGroupRequest;
import dto.response.ExpenseGroupResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import java.util.List;

public interface ExpenseGroupService {
    // Create a new group
    ExpenseGroupResponse createGroup(ExpenseGroupRequest request) 
        throws ResourceNotFoundException, ConflictException;
    
    // Get group by ID
    ExpenseGroupResponse getGroupById(Long groupId) 
        throws ResourceNotFoundException;
    
    // Get all groups
    List<ExpenseGroupResponse> getAllGroups();
    
    // Update group name
    ExpenseGroupResponse updateGroup(Long groupId, ExpenseGroupRequest request) 
        throws ResourceNotFoundException;
    
    // Delete group and its members/expenses
    void deleteGroup(Long groupId) 
        throws ResourceNotFoundException;
}