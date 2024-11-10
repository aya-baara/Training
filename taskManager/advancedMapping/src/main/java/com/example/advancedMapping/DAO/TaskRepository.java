package com.example.advancedMapping.DAO;

import com.example.advancedMapping.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {
}
