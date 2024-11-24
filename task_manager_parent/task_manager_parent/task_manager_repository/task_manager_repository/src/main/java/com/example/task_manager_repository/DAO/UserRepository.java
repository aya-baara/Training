package com.example.task_manager_repository.DAO;

import com.example.task_manager_repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User,Integer>{

    Optional<User> findByUsername(String username);

}
