package com.project.todolist.AuthBackEnd.repository;


import com.project.todolist.AuthBackEnd.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> findByEmail(String email);

}
