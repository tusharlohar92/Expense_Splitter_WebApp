package service;

import dto.request.UserRequest;
import dto.response.UserResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request) throws ConflictException;
    UserResponse getUserById(Long userId) throws ResourceNotFoundException;
    UserResponse getUserByEmail(String email) throws ResourceNotFoundException;
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long userId, UserRequest request) throws ResourceNotFoundException;
    void deleteUser(Long userId) throws ResourceNotFoundException;
}