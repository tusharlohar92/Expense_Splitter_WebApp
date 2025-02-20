package controller;

import dto.request.ExpenseRequest;
import dto.response.ExpenseResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ExpenseService;
import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 * REST controller for managing expense-related operations.
 * Handles creation, retrieval, and deletion of expenses.
 */
@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    /**
     * Creates a new expense.
     * 
     * @param request Expense creation request
     * @return Created expense with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@RequestBody ExpenseRequest request) 
        throws ResourceNotFoundException, ConflictException {
        ExpenseResponse response = expenseService.createExpense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves an expense by its ID.
     * 
     * @param expenseId ID of the expense to retrieve
     * @return Expense details with HTTP 200 status
     */
    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long expenseId) 
        throws ResourceNotFoundException {
        return ResponseEntity.ok(expenseService.getExpenseById(expenseId));
    }

    /**
     * Retrieves expenses associated with a group.
     * 
     * @param groupId ID of the group (null for non-group expenses)
     * @return List of expenses with HTTP 200 status
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByGroup(@PathVariable(required = false) Long groupId) 
        throws ResourceNotFoundException {
        return ResponseEntity.ok(expenseService.getExpensesByGroup(groupId));
    }

    /**
     * Retrieves expenses paid by a specific user.
     * 
     * @param userId ID of the user
     * @return List of expenses with HTTP 200 status
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByUser(@PathVariable Long userId) 
        throws ResourceNotFoundException {
        return ResponseEntity.ok(expenseService.getExpensesByUser(userId));
    }

    /**
     * Deletes an expense by its ID.
     * 
     * @param expenseId ID of the expense to delete
     * @return HTTP 204 No Content status
     */
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) 
        throws ResourceNotFoundException {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }
}