package service.impl;

import dto.request.ExpenseRequest;
import dto.response.ExpenseResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.*;
import service.ExpenseService;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
//import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ExpenseGroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final ExpenseShareRepository expenseShareRepository;
    private final DebtRepository debtRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ExpenseResponse createExpense(ExpenseRequest request) 
        throws ResourceNotFoundException, ConflictException {
        
        // Validate payer
        User paidBy = userRepository.findById(request.getPaidBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getPaidBy()));

        // Validate group (if provided)
        ExpenseGroup group = null;
        if (request.getGroupId() != null) {
            group = groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("Group", "id", request.getGroupId()));
            
            // Verify payer is group member
            if (!groupMemberRepository.existsByGroupAndUser(group, paidBy)) {
                throw new ConflictException("Payer is not a group member");
            }
        }

        // Create and save expense
        Expense expense = modelMapper.map(request, Expense.class);
        expense.setPaidBy(paidBy);
        expense.setGroup(group);
        Expense savedExpense = expenseRepository.save(expense);

        // Validate shares
        BigDecimal totalShares = request.getShares().values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        if (totalShares.compareTo(savedExpense.getAmount()) != 0) {
            throw new ConflictException("Total shares must equal expense amount");
        }

        // Save shares and calculate debts
        final ExpenseGroup finalGroup = group;
        request.getShares().forEach((userId, share) -> {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
            
            if (finalGroup != null && !groupMemberRepository.existsByGroupAndUser(finalGroup, user)) {
                throw new ConflictException("User " + userId + " is not a group member");
            }

            ExpenseShare expenseShare = new ExpenseShare();
            expenseShare.setExpense(savedExpense);
            expenseShare.setUser(user);
            expenseShare.setShare(share);
            expenseShareRepository.save(expenseShare);
        });

        // Calculate debts
        calculateAndUpdateDebts(savedExpense, request.getShares());

        return modelMapper.map(savedExpense, ExpenseResponse.class);
    }

    private void calculateAndUpdateDebts(Expense expense, Map<Long, BigDecimal> shares) {
        User payer = expense.getPaidBy();
        
        shares.forEach((userId, shareAmount) -> {
            if (userId.equals(payer.getUserId())) return;
            
            User debtor = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + userId));

            debtRepository.findByOwedByAndOwedTo(debtor, payer)
                .ifPresentOrElse(
                    debt -> {
                        debt.setAmount(debt.getAmount().add(shareAmount));
                        debtRepository.save(debt);
                    },
                    () -> {
                        Debt newDebt = new Debt();
                        newDebt.setOwedBy(debtor);
                        newDebt.setOwedTo(payer);
                        newDebt.setAmount(shareAmount);
                        newDebt.setGroup(expense.getGroup());
                        debtRepository.save(newDebt);
                    }
                );
        });
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseResponse getExpenseById(Long expenseId) throws ResourceNotFoundException {
        return expenseRepository.findById(expenseId)
                .map(expense -> modelMapper.map(expense, ExpenseResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", expenseId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getExpensesByGroup(Long groupId) {
        return expenseRepository.findByGroupGroupId(groupId).stream()
                .map(expense -> modelMapper.map(expense, ExpenseResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getExpensesByUser(Long userId) {
        return expenseRepository.findByPaidBy_UserId(userId).stream()
                .map(expense -> modelMapper.map(expense, ExpenseResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteExpense(Long expenseId) throws ResourceNotFoundException {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", expenseId));
        
        // Delete associated shares first
        expenseShareRepository.deleteByExpense(expense);
        
        // Delete the expense
        expenseRepository.delete(expense);
    }

    @Override
    @Transactional
    public void settleDebt(Long debtId) throws ResourceNotFoundException {
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new ResourceNotFoundException("Debt", "id", debtId));
        
        debt.setIsSettled(true);
        debt.setSettledAt(LocalDateTime.now());
        debtRepository.save(debt);
    }
}