package com.blogspot.regulargeek.telegrambot.message.commands;

import com.blogspot.regulargeek.telegrambot.message.TelegramCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class HelpCommand extends TelegramCommand {
    private String commandName;

    public HelpCommand(SendMessage message, String commandName) {
        super(message);
        this.commandName = commandName!=null?commandName.toUpperCase():null;
    }

    public String getCommandName() {
        return commandName;
    }
}
