package com.example.demoquizbot.service;

import com.example.demoquizbot.entity.QuestionEntity;
import com.example.demoquizbot.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionEntity> getAllQuestion(){
        return questionRepository.findAll();
    }
}
