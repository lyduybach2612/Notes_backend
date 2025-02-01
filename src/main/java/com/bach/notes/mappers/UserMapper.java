package com.bach.notes.mappers;

import com.bach.notes.dtos.requests.users.UserCreationRequest;
import com.bach.notes.dtos.responses.users.UserResponse;
import com.bach.notes.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface UserMapper {

    UserResponse toUserResponse(User user);
    User toUser(UserCreationRequest user);

}
