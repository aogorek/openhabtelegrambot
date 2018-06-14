package com.blogspot.regulargeek.telegrambot.message.handler;

import com.blogspot.regulargeek.telegrambot.message.TelegramCommand;
import com.blogspot.regulargeek.telegrambot.event.TelegramCommandResponseReadyEvent;
import com.blogspot.regulargeek.telegrambot.service.RestApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public abstract class TelegramCommandHandler<C extends TelegramCommand> {
    public abstract void handle(C command);

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    protected RestApiService restApiService;

    protected void sendResponse(SendMessage message) {

        TelegramCommandResponseReadyEvent responseReadyEvent = new TelegramCommandResponseReadyEvent(message);
        applicationEventPublisher.publishEvent(responseReadyEvent);

    }
}
