package controller;

import dto.request.ExpenseShareRequest;
import dto.response.ExpenseShareResponse;
import exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ExpenseShareService;
import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 * REST controller for managing expense share allocations.
 * Handles creation, retrieval, and deletion of expense shares.
 */
@RestController
@RequestMapping("/api/expenses/shares")
@RequiredArgsConstructor
public class ExpenseShareController {

    private final ExpenseShareService expenseShareService;

    /**
     * Creates a new expense share allocation.
     * 
     * @param request Expense share creation request
     * @return Created share with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<ExpenseShareResponse> createShare(@RequestBody ExpenseShareRequest request) 
        throws ResourceNotFoundException {
        ExpenseShareResponse response = expenseShareService.createShare(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves all shares for a specific expense.
     * 
     * @param expenseId ID of the expense
     * @return List of shares with HTTP 200 status
     */
    @GetMapping("/expense/{expenseId}")
    public ResponseEntity<List<ExpenseShareResponse>> getSharesByExpense(@PathVariable Long expenseId) 
        throws ResourceNotFoundException {
        return ResponseEntity.ok(expenseShareService.getSharesByExpense(expenseId));
    }

    /**
     * Deletes a specific share allocation.
     * 
     * @param expenseId ID of the expense
     * @param userId ID of the user in the share
     * @return HTTP 204 No Content status
     */
    @DeleteMapping("/expense/{expenseId}/user/{userId}")
    public ResponseEntity<Void> deleteShare(
        @PathVariable Long expenseId,
        @PathVariable Long userId
    ) throws ResourceNotFoundException {
        expenseShareService.deleteShare(expenseId, userId);
        return ResponseEntity.noContent().build();
    }
}