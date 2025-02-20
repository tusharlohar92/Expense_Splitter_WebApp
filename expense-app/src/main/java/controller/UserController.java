package controller;

import dto.request.UserRequest;
import dto.response.UserResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;
import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 * REST controller for user management.
 * Handles user creation, retrieval, updates, and deletion.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    /**
     * Creates a new user.
     * 
     * @param request User details
     * @return Created user with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) 
        throws ConflictException {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves a user by ID.
     * 
     * @param userId ID of the user
     * @return User details with HTTP 200 status
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) 
        throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    /**
     * Retrieves a user by email.
     * 
     * @param email User's email address
     * @return User details with HTTP 200 status
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) 
        throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    /**
     * Retrieves all users.
     * 
     * @return List of users with HTTP 200 status
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Updates user details.
     * 
     * @param userId ID of the user to update
     * @param request Updated user details
     * @return Updated user with HTTP 200 status
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
        @PathVariable Long userId,
        @RequestBody UserRequest request
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    /**
     * Deletes a user.
     * 
     * @param userId ID of the user to delete
     * @return HTTP 204 No Content status
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) 
        throws ResourceNotFoundException {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
