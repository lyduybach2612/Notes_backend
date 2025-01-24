package com.bach.notes.repositories;

import com.bach.notes.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
