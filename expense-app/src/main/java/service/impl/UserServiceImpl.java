package service.impl;

import dto.request.UserRequest;
import dto.response.UserResponse;
import exception.ConflictException;
import exception.ResourceNotFoundException;
import model.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;
import service.UserService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) throws ConflictException {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already registered");
        }
        
        User user = modelMapper.map(request, User.class);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) throws ResourceNotFoundException {
        return userRepository.findById(userId)
                .map(user -> modelMapper.map(user, UserResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) throws ResourceNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> modelMapper.map(user, UserResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long userId, UserRequest request) throws ResourceNotFoundException {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setName(request.getName());
                    if(request.getPassword() != null) {
                        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
                    }
                    User updatedUser = userRepository.save(user);
                    return modelMapper.map(updatedUser, UserResponse.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepository.delete(user);
    }
}