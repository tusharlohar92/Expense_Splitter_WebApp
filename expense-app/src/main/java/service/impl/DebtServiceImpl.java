package service.impl;

import dto.request.DebtRequest;
import dto.response.DebtResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.DebtRepository;
import repository.ExpenseGroupRepository;
import repository.UserRepository;
import service.DebtService;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DebtServiceImpl implements DebtService {

    private final DebtRepository debtRepository;
    private final UserRepository userRepository;
    private final ExpenseGroupRepository groupRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public DebtResponse createDebt(DebtRequest request) 
        throws ResourceNotFoundException, ConflictException {
        
        User owedBy = userRepository.findById(request.getOwedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getOwedBy()));
        
        User owedTo = userRepository.findById(request.getOwedTo())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getOwedTo()));

        if (owedBy.equals(owedTo)) {
            throw new ConflictException("Owed By and Owed To cannot be the same user");
        }

        ExpenseGroup group = null;
        if (request.getGroupId() != null) {
            group = groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("Group", "id", request.getGroupId()));
        }

        Debt debt = modelMapper.map(request, Debt.class);
        debt.setOwedBy(owedBy);
        debt.setOwedTo(owedTo);
        debt.setGroup(group);
        Debt savedDebt = debtRepository.save(debt);

        return modelMapper.map(savedDebt, DebtResponse.class);
    }

    @Override
    @Transactional
    public DebtResponse settleDebt(Long debtId) throws ResourceNotFoundException {
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new ResourceNotFoundException("Debt", "id", debtId));
        
        if (!debt.getIsSettled()) {
            debt.setIsSettled(true);
            debt.setSettledAt(LocalDateTime.now());
            Debt updatedDebt = debtRepository.save(debt);
            return modelMapper.map(updatedDebt, DebtResponse.class);
        }
        return modelMapper.map(debt, DebtResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DebtResponse> getDebtsByOwedBy(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        return debtRepository.findByOwedBy(user).stream()
                .map(debt -> modelMapper.map(debt, DebtResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DebtResponse> getDebtsByOwedTo(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        return debtRepository.findByOwedTo(user).stream()
                .map(debt -> modelMapper.map(debt, DebtResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DebtResponse> getDebtsByGroup(Long groupId) throws ResourceNotFoundException {
        ExpenseGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));
        
        return debtRepository.findByGroup(group).stream()
                .map(debt -> modelMapper.map(debt, DebtResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DebtResponse> getDebtsBySettlementStatus(boolean isSettled) {
        return debtRepository.findByIsSettled(isSettled).stream()
                .map(debt -> modelMapper.map(debt, DebtResponse.class))
                .collect(Collectors.toList());
    }
}