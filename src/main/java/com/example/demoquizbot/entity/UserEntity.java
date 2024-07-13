package com.example.demoquizbot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long chatId;

    private Integer score;

    private Integer questionIndex;

    public UserEntity(){
        // Default constructor is required by Hibernate
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(Integer questionIndex) {
        this.questionIndex = questionIndex;
    }
}
