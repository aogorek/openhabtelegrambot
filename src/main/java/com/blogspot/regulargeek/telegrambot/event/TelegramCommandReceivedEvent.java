package com.blogspot.regulargeek.telegrambot.event;

import org.springframework.context.ApplicationEvent;

public class TelegramCommandReceivedEvent extends ApplicationEvent {
    public TelegramCommandReceivedEvent(Object source) {
        super(source);
    }
}
