package com.example.demoquizbot.service;

import com.example.demoquizbot.entity.QuestionEntity;
import com.example.demoquizbot.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;
import java.util.Optional;

@Component
public class QuizGameService {

    private final UserService userService;

    private final QuestionService questionService;

    private final TelegramClient telegramClient;

    public QuizGameService(UserService userService, QuestionService questionService, TelegramClient telegramClient) {
        this.userService = userService;
        this.questionService = questionService;
        this.telegramClient = telegramClient;
    }


    public void startGame(Long chatId) throws TelegramApiException {
        UserEntity userEntity = userService.getUserByChatId(chatId).orElse(new UserEntity());
        userEntity.setChatId(chatId);
        userEntity.setQuestionIndex(0);
        userEntity.setScore(0);
        userService.save(userEntity);
        sendQuestion(chatId);
    }

    public  void handleAnswer(Long chatId, Integer answerIndex, Integer messageId) throws TelegramApiException{
        Optional<UserEntity> userEntity = userService.getUserByChatId(chatId);
        List<QuestionEntity> questions = questionService.getAllQuestion();

        if (userEntity.isPresent()){
            UserEntity user = userEntity.get();

            QuestionEntity question = questions.get(user.getQuestionIndex());

            if (answerIndex.equals(question.getAnswerIndex())){
                user.setScore(user.getScore()+1);
            }
            user.setQuestionIndex(user.getQuestionIndex()+1);
            userService.save(user);

            EditMessageText message = EditMessageText.builder()
                            .chatId(chatId)
                            .messageId(messageId)
                            .text("Вы выбрали вариант "+answerIndex)
                            .build();

            telegramClient.execute(message);

            sendQuestion(chatId);
        }
    }

    private void sendQuestion(Long chatId) throws TelegramApiException {
        Optional<UserEntity> userEntity = userService.getUserByChatId(chatId);
        List<QuestionEntity> questions = questionService.getAllQuestion();

        if (userEntity.isPresent()){
            UserEntity user = userEntity.get();
            int questionIndex = user.getQuestionIndex();

            if (questionIndex < questions.size()){
                QuestionEntity question = questions.get(questionIndex);

                SendMessage message = SendMessage.builder()
                        .chatId(chatId)
                        .text(question.getQuestion())
                        .replyMarkup(InlineKeyboardMarkup.builder()
                                .keyboardRow(new InlineKeyboardRow(
                                        InlineKeyboardButton.builder().text(question.getOption1()).callbackData("1").build(),
                                        InlineKeyboardButton.builder().text(question.getOption2()).callbackData("2").build(),
                                        InlineKeyboardButton.builder().text(question.getOption3()).callbackData("3").build(),
                                        InlineKeyboardButton.builder().text(question.getOption4()).callbackData("4").build()
                                ))
                                .build())
                        .build();

                telegramClient.execute(message);

            } else {
                endQuiz(chatId);
            }
        }

    }

    private void endQuiz(Long chatId) throws TelegramApiException {
        Optional<UserEntity> userEntity = userService.getUserByChatId(chatId);

        if (userEntity.isPresent()){
            UserEntity user = userEntity.get();
            int score = user.getScore();

            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text("Поздравляем! Вы закончили викторину с результатом "+score + " очков")
                    .build();
            telegramClient.execute(message);
            userService.remove(user);
        }
    }
}
