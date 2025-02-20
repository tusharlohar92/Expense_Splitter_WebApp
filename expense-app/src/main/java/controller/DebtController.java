package controller;

import dto.request.DebtRequest;
import dto.response.DebtResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.DebtService;
import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 *REST controller for managing debt-related operations.
 * Handles creation, settlement, and retrieval of debts.
 */
@RestController
@RequestMapping("/api/debts")
@RequiredArgsConstructor
public class DebtController {

    private final DebtService debtService;

    /**
     * Creates a new debt record.
     * 
     * @param request Debt creation request body
     * @return Created debt with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<DebtResponse> createDebt(@RequestBody DebtRequest request) 
        throws ResourceNotFoundException, ConflictException {
        DebtResponse response = debtService.createDebt(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Marks a debt as settled.
     * 
     * @param debtId ID of the debt to settle
     * @return Updated debt with HTTP 200 status
     */
    @PutMapping("/{debtId}/settle")
    public ResponseEntity<DebtResponse> settleDebt(@PathVariable Long debtId) 
        throws ResourceNotFoundException {
        return ResponseEntity.ok(debtService.settleDebt(debtId));
    }

    /**
     * Retrieves all debts where the specified user owes money.
     * 
     * @param userId ID of the debtor
     * @return List of debts with HTTP 200 status
     */
    @GetMapping("/owed-by/{userId}")
    public ResponseEntity<List<DebtResponse>> getDebtsByOwedBy(@PathVariable Long userId) 
        throws ResourceNotFoundException {
        return ResponseEntity.ok(debtService.getDebtsByOwedBy(userId));
    }

    /**
     * Retrieves all debts owed to the specified user.
     * 
     * @param userId ID of the creditor
     * @return List of debts with HTTP 200 status
     */
    @GetMapping("/owed-to/{userId}")
    public ResponseEntity<List<DebtResponse>> getDebtsByOwedTo(@PathVariable Long userId) 
        throws ResourceNotFoundException {
        return ResponseEntity.ok(debtService.getDebtsByOwedTo(userId));
    }

    /**
     * Retrieves all debts associated with a group.
     * 
     * @param groupId ID of the group
     * @return List of debts with HTTP 200 status
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<DebtResponse>> getDebtsByGroup(@PathVariable Long groupId) 
        throws ResourceNotFoundException {
        return ResponseEntity.ok(debtService.getDebtsByGroup(groupId));
    }

    /**
     * Retrieves debts based on their settlement status.
     * 
     * @param isSettled True for settled debts, false for unsettled
     * @return List of debts with HTTP 200 status
     */
    @GetMapping("/settled")
    public ResponseEntity<List<DebtResponse>> getSettledDebts(
        @RequestParam(defaultValue = "true") boolean isSettled) {
        return ResponseEntity.ok(debtService.getDebtsBySettlementStatus(isSettled));
    }
}