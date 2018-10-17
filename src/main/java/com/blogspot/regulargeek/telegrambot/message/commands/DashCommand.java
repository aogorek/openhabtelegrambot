package com.blogspot.regulargeek.telegrambot.message.commands;

import com.blogspot.regulargeek.telegrambot.message.TelegramCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class DashCommand extends TelegramCommand {

    public DashCommand(SendMessage message) {
        super(message);
    }
}

