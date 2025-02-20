
package service.impl;

import dto.request.GroupMemberRequest;
import dto.response.GroupMemberResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import model.ExpenseGroup;
import model.GroupMember;
import model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ExpenseGroupRepository;
import repository.GroupMemberRepository;
import repository.UserRepository;
import service.GroupMemberService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupMemberServiceImpl implements GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;
    private final ExpenseGroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public GroupMemberResponse addMemberToGroup(GroupMemberRequest request) 
        throws ResourceNotFoundException, ConflictException {
        
        ExpenseGroup group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", request.getGroupId()));
        
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));

        if (groupMemberRepository.existsByGroupAndUser(group, user)) {
            throw new ConflictException("User is already a group member");
        }

        GroupMember member = new GroupMember();
        member.setGroup(group);
        member.setUser(user);
        GroupMember savedMember = groupMemberRepository.save(member);

        return modelMapper.map(savedMember, GroupMemberResponse.class);
    }

    @Override
    @Transactional
    public void removeMemberFromGroup(Long groupId, Long userId) 
        throws ResourceNotFoundException {
        
        GroupMember.GroupMemberId id = new GroupMember.GroupMemberId(groupId, userId);
        GroupMember member = groupMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group member", "id", id.toString()));
        groupMemberRepository.delete(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupMemberResponse> getGroupMembers(Long groupId) 
        throws ResourceNotFoundException {
        
        ExpenseGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));
        
        return groupMemberRepository.findByGroup(group).stream()
                .map(member -> modelMapper.map(member, GroupMemberResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupMemberResponse> getUserGroups(Long userId) 
        throws ResourceNotFoundException {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        return groupMemberRepository.findByUser(user).stream()
                .map(member -> modelMapper.map(member, GroupMemberResponse.class))
                .collect(Collectors.toList());
    }
}