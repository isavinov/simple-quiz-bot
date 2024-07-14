package com.example.demoquizbot.controller;

import com.example.demoquizbot.entity.QuestionEntity;
import com.example.demoquizbot.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public Collection<QuestionEntity> getAllQuestion(){
        return questionService.getAllQuestion();
    }

    @PostMapping
    public void createQuestion(@RequestBody QuestionEntity question){
        questionService.addQuestion(question);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable("id") Long id){
        questionService.removeQuestion(id);
    }
}
