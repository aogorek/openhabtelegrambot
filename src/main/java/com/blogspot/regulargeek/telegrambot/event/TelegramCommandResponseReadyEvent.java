package com.blogspot.regulargeek.telegrambot.event;

import org.springframework.context.ApplicationEvent;

public class TelegramCommandResponseReadyEvent extends ApplicationEvent {
    public TelegramCommandResponseReadyEvent(Object source) {
        super(source);
    }
}
