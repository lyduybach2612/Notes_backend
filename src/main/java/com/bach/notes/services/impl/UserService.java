package com.bach.notes.services.impl;

import com.bach.notes.repositories.UserRepository;
import com.bach.notes.services.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService implements IUserService {

    UserRepository userRepository;

}
