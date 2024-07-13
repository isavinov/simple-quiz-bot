package com.example.demoquizbot.repository;

import com.example.demoquizbot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByChatId(Long chatId);

}
