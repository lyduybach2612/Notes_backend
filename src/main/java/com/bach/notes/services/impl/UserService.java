package com.bach.notes.services.impl;

import com.bach.notes.dtos.requests.users.UserCreationRequest;
import com.bach.notes.dtos.responses.users.UserResponse;
import com.bach.notes.exceptions.AppException;
import com.bach.notes.exceptions.ErrorCode;
import com.bach.notes.mappers.UserMapper;
import com.bach.notes.models.User;
import com.bach.notes.repositories.UserRepository;
import com.bach.notes.services.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService implements IUserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreationRequest request) {

        User existingEmail = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (existingEmail != null) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User existingUsername = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (existingUsername != null) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = userRepository.save(userMapper.toUser(request));
        return userMapper.toUserResponse(user);

    }

    @Override
    public UserResponse getMyInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

}
