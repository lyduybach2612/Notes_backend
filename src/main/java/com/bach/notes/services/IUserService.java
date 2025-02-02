package com.bach.notes.services;

import com.bach.notes.dtos.requests.users.UserCreationRequest;
import com.bach.notes.dtos.responses.users.UserResponse;

public interface IUserService {

    UserResponse createUser(UserCreationRequest request);
    UserResponse getMyInfo();

}
