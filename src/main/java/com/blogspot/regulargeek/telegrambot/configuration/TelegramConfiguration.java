package com.blogspot.regulargeek.telegrambot.configuration;

import com.blogspot.regulargeek.telegrambot.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Configuration
public class TelegramConfiguration {

    @Autowired
    private TelegramBot telegramBot;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
