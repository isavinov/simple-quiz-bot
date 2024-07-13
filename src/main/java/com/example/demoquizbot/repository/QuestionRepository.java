package com.example.demoquizbot.repository;

import com.example.demoquizbot.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
}
