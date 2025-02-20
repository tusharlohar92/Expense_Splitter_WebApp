package service;

import dto.request.DebtRequest;
import dto.response.DebtResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import java.util.List;

public interface DebtService {
    DebtResponse createDebt(DebtRequest request) 
        throws ResourceNotFoundException, ConflictException;
        
    DebtResponse settleDebt(Long debtId) 
        throws ResourceNotFoundException;
        
    List<DebtResponse> getDebtsByOwedBy(Long userId) 
        throws ResourceNotFoundException;
        
    List<DebtResponse> getDebtsByOwedTo(Long userId) 
        throws ResourceNotFoundException;
        
    List<DebtResponse> getDebtsByGroup(Long groupId) 
        throws ResourceNotFoundException;
        
    List<DebtResponse> getDebtsBySettlementStatus(boolean isSettled);
}