package com.example.advancedMapping.DAO;
import com.example.advancedMapping.entity.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokenRepository  extends JpaRepository<Tokens,String> {
    @Transactional
    void deleteAllByUserId(Long userId);
}
