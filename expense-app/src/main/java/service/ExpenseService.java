package service;

import dto.request.ExpenseRequest;
import dto.response.ExpenseResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import java.util.List;

public interface ExpenseService {
    ExpenseResponse createExpense(ExpenseRequest request) 
        throws ResourceNotFoundException, ConflictException;
    ExpenseResponse getExpenseById(Long expenseId) throws ResourceNotFoundException;
    List<ExpenseResponse> getExpensesByGroup(Long groupId) throws ResourceNotFoundException;
    List<ExpenseResponse> getExpensesByUser(Long userId) throws ResourceNotFoundException;
    void deleteExpense(Long expenseId) throws ResourceNotFoundException;
    void settleDebt(Long debtId) throws ResourceNotFoundException; // Added method
}