package service;

import dto.request.ExpenseShareRequest;
import dto.response.ExpenseShareResponse;
import exception.ResourceNotFoundException;
import java.util.List;

public interface ExpenseShareService {
    ExpenseShareResponse createShare(ExpenseShareRequest request) 
        throws ResourceNotFoundException;
    
    List<ExpenseShareResponse> getSharesByExpense(Long expenseId) 
        throws ResourceNotFoundException;
    
    void deleteShare(Long expenseId, Long userId) 
        throws ResourceNotFoundException;
}
