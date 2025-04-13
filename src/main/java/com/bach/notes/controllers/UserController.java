package com.bach.notes.controllers;

import com.bach.notes.dtos.requests.users.UserCreationRequest;
import com.bach.notes.dtos.responses.ApiResponse;
import com.bach.notes.dtos.responses.users.UserResponse;
import com.bach.notes.services.impl.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;


@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    UserService userService;

    @PostMapping()
    public ApiResponse<UserResponse> register(@RequestBody @Valid UserCreationRequest request) {

        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.createUser(request))
                .build();

    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.getMyInfo())
                .build();
    }

}
