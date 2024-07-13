package com.example.demoquizbot.service;

import com.example.demoquizbot.entity.UserEntity;
import com.example.demoquizbot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> getUserByChatId(Long chatId){
        return userRepository.findByChatId(chatId);
    }

    public UserEntity save(UserEntity user){
        return userRepository.save(user);
    }

    public void remove(UserEntity user){
        userRepository.delete(user);
    }
}
