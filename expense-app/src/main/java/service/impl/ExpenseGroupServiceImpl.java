package service.impl;

import dto.request.ExpenseGroupRequest;
import dto.response.ExpenseGroupResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import model.ExpenseGroup;
import model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ExpenseGroupRepository;
import repository.UserRepository;
import service.ExpenseGroupService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseGroupServiceImpl implements ExpenseGroupService {

    private final ExpenseGroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ExpenseGroupResponse createGroup(ExpenseGroupRequest request) 
        throws ResourceNotFoundException, ConflictException {
        
        // Validate creator exists
        User creator = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() -> 
                    new ResourceNotFoundException("User", "id", request.getCreatedBy()));

        // Check for duplicate group name (optional)
        if (groupRepository.existsByName(request.getName())) {
            throw new ConflictException("Group name already exists");
        }

        // Create and save group
        ExpenseGroup group = modelMapper.map(request, ExpenseGroup.class);
        group.setCreatedBy(creator);
        ExpenseGroup savedGroup = groupRepository.save(group);
        
        return modelMapper.map(savedGroup, ExpenseGroupResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseGroupResponse getGroupById(Long groupId) throws ResourceNotFoundException {
        return groupRepository.findById(groupId)
                .map(group -> modelMapper.map(group, ExpenseGroupResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseGroupResponse> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(group -> modelMapper.map(group, ExpenseGroupResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ExpenseGroupResponse updateGroup(Long groupId, ExpenseGroupRequest request) 
        throws ResourceNotFoundException {
        
        return groupRepository.findById(groupId)
                .map(group -> {
                    group.setName(request.getName());
                    ExpenseGroup updatedGroup = groupRepository.save(group);
                    return modelMapper.map(updatedGroup, ExpenseGroupResponse.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));
    }

    @Override
    @Transactional
    public void deleteGroup(Long groupId) throws ResourceNotFoundException {
        ExpenseGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));
        groupRepository.delete(group);
    }
}