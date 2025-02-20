package service.impl;

import dto.request.ExpenseShareRequest;
import dto.response.ExpenseShareResponse;
import exception.ResourceNotFoundException;
import model.Expense;
import model.ExpenseShare;
import model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ExpenseRepository;
import repository.ExpenseShareRepository;
import repository.UserRepository;
import service.ExpenseShareService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseShareServiceImpl implements ExpenseShareService {

    private final ExpenseShareRepository expenseShareRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ExpenseShareResponse createShare(ExpenseShareRequest request) 
        throws ResourceNotFoundException {
        
        // Validate expense exists
        Expense expense = expenseRepository.findById(request.getExpenseId())
                .orElseThrow(() -> 
                    new ResourceNotFoundException("Expense", "id", request.getExpenseId()));
        
        // Validate user exists
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> 
                    new ResourceNotFoundException("User", "id", request.getUserId()));

        // Create and save share
        ExpenseShare share = new ExpenseShare();
        share.setExpense(expense);
        share.setUser(user);
        share.setShare(request.getShare());
        ExpenseShare savedShare = expenseShareRepository.save(share);

        return modelMapper.map(savedShare, ExpenseShareResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseShareResponse> getSharesByExpense(Long expenseId) 
        throws ResourceNotFoundException {
        
        // Validate expense exists
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> 
                    new ResourceNotFoundException("Expense", "id", expenseId));
        
        return expenseShareRepository.findByExpense(expense).stream()
                .map(share -> modelMapper.map(share, ExpenseShareResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteShare(Long expenseId, Long userId) 
        throws ResourceNotFoundException {
        
        // Composite key
        ExpenseShare.ExpenseShareId id = 
            new ExpenseShare.ExpenseShareId(expenseId, userId);
        
        // Validate share exists
        ExpenseShare share = expenseShareRepository.findById(id)
                .orElseThrow(() -> 
                    new ResourceNotFoundException("Expense share", "id", id.toString()));
        
        expenseShareRepository.delete(share);
    }
}