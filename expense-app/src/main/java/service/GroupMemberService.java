package service;

import dto.request.GroupMemberRequest;
import dto.response.GroupMemberResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import java.util.List;

public interface GroupMemberService {
    GroupMemberResponse addMemberToGroup(GroupMemberRequest request) 
        throws ResourceNotFoundException, ConflictException;
    void removeMemberFromGroup(Long groupId, Long userId) 
        throws ResourceNotFoundException;
    List<GroupMemberResponse> getGroupMembers(Long groupId) 
        throws ResourceNotFoundException;
    List<GroupMemberResponse> getUserGroups(Long userId) 
        throws ResourceNotFoundException;
}