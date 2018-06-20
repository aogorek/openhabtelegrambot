package com.blogspot.regulargeek.telegrambot.message.commands;

import com.blogspot.regulargeek.telegrambot.message.TelegramCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class SwitchesCommand extends TelegramCommand {

    public SwitchesCommand(SendMessage message) {
        super(message);
    }
}
