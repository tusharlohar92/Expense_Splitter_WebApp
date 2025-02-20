package controller;

import dto.request.GroupMemberRequest;
import dto.response.GroupMemberResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.GroupMemberService;
import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 * REST controller for managing group memberships.
 * Handles adding/removing members and retrieving group/user associations.
 */
@RestController
@RequestMapping("/api/groups/members")
@RequiredArgsConstructor
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    /**
     * Adds a user to a group.
     * 
     * @param request Contains groupId and userId
     * @return Created membership with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<GroupMemberResponse> addMember(@RequestBody GroupMemberRequest request) 
        throws ResourceNotFoundException, ConflictException {
        GroupMemberResponse response = groupMemberService.addMemberToGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Removes a user from a group.
     * 
     * @param groupId ID of the group
     * @param userId ID of the user to remove
     * @return HTTP 204 No Content status
     */
    @DeleteMapping("/{groupId}/users/{userId}")
    public ResponseEntity<Void> removeMember(
        @PathVariable Long groupId,
        @PathVariable Long userId
    ) throws ResourceNotFoundException {
        groupMemberService.removeMemberFromGroup(groupId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all members of a group.
     * 
     * @param groupId ID of the group
     * @return List of memberships with HTTP 200 status
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<GroupMemberResponse>> getGroupMembers(@PathVariable Long groupId) 
        throws ResourceNotFoundException {
        return ResponseEntity.ok(groupMemberService.getGroupMembers(groupId));
    }

    /**
     * Retrieves all groups a user belongs to.
     * 
     * @param userId ID of the user
     * @return List of memberships with HTTP 200 status
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GroupMemberResponse>> getUserGroups(@PathVariable Long userId) 
        throws ResourceNotFoundException {
        return ResponseEntity.ok(groupMemberService.getUserGroups(userId));
    }
}
