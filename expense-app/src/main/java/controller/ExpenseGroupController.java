package controller;

import dto.request.ExpenseGroupRequest;
import dto.response.ExpenseGroupResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ExpenseGroupService;
import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 * REST controller for managing expense groups.
 * Handles group creation, updates, and retrieval.
 */
@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class ExpenseGroupController {

    private final ExpenseGroupService groupService;

    /**
     * Creates a new expense group.
     * 
     * @param request Group creation request
     * @return Created group with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<ExpenseGroupResponse> createGroup(@RequestBody ExpenseGroupRequest request) 
        throws ResourceNotFoundException, ConflictException {
        ExpenseGroupResponse response = groupService.createGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves a group by its ID.
     * 
     * @param groupId ID of the group to retrieve
     * @return Group details with HTTP 200 status
     */
    @GetMapping("/{groupId}")
    public ResponseEntity<ExpenseGroupResponse> getGroupById(@PathVariable Long groupId) 
        throws ResourceNotFoundException {
        return ResponseEntity.ok(groupService.getGroupById(groupId));
    }

    /**
     * Retrieves all existing groups.
     * 
     * @return List of groups with HTTP 200 status
     */
    @GetMapping
    public ResponseEntity<List<ExpenseGroupResponse>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    /**
     * Updates an existing group's details.
     * 
     * @param groupId ID of the group to update
     * @param request Updated group data
     * @return Updated group with HTTP 200 status
     */
    @PutMapping("/{groupId}")
    public ResponseEntity<ExpenseGroupResponse> updateGroup(
        @PathVariable Long groupId,
        @RequestBody ExpenseGroupRequest request
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(groupService.updateGroup(groupId, request));
    }

    /**
     * Deletes a group by its ID.
     * 
     * @param groupId ID of the group to delete
     * @return HTTP 204 No Content status
     */
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) 
        throws ResourceNotFoundException {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }
}
