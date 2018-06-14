package com.blogspot.regulargeek.telegrambot.message;

import org.telegram.telegrambots.api.methods.send.SendMessage;

public abstract class TelegramCommand {
    protected SendMessage sendMessage;

    public TelegramCommand(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }
}
