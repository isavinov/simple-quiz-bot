package com.example.demoquizbot.listener;

import com.example.demoquizbot.service.QuizGameService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class QuizTelegramBotUpdateListener implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final String token;

    private final QuizGameService quizGameService;

    public QuizTelegramBotUpdateListener(@Value("${botToken}") String token, QuizGameService quizGameService) {
        this.token = token;
        this.quizGameService = quizGameService;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.equalsIgnoreCase("/start")) {
                try {
                    quizGameService.startGame(chatId);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            int messageId = update.getCallbackQuery().getMessage().getMessageId();

            try {
                quizGameService.handleAnswer(chatId, Integer.valueOf(callbackData), messageId);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
