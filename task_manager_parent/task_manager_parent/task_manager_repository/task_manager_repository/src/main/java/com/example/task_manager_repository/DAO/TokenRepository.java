package com.example.task_manager_repository.DAO;
import com.example.task_manager_repository.entity.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokenRepository  extends JpaRepository<Tokens,String> {
    @Transactional
    void deleteAllByUserId(int userId);
    Optional<Tokens> findByJwtToken(String token);
}
