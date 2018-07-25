package com.blogspot.regulargeek.telegrambot.message.commands;

import com.blogspot.regulargeek.telegrambot.message.ItemCommand;
import com.blogspot.regulargeek.telegrambot.message.TelegramCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class MeteoCommand extends TelegramCommand {

    public MeteoCommand(SendMessage message) {
        super(message);
    }

}
