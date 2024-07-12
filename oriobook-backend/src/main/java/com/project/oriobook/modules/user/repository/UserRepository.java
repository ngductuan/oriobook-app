package com.project.oriobook.modules.user.repository;

import com.project.oriobook.modules.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email) throws Exception;
}
