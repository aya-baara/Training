package com.example.advancedMapping.DAO;

import com.example.advancedMapping.entity.Task;
import com.example.advancedMapping.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {

    void deleteAllByUser_Id(int id);
    Page<Task> findAllByUser_Id(int id, Pageable pageable);
    Task findById(int id);
    /// find tasks that ends after the task start date and begin before the end date of the task (conflict check)
    List<Task> findAllByUser_IdAndEndDateIsAfterAndStartDateBefore
            (int id, @Param("start") LocalDate start, @Param("end") LocalDate end);

}
