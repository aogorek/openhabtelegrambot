package com.blogspot.regulargeek.telegrambot.message.parser;

import com.blogspot.regulargeek.telegrambot.message.TelegramCommand;
import com.blogspot.regulargeek.telegrambot.exception.CommandParseException;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public interface SingleCommandParser {
    TelegramCommand parse(SendMessage message) throws CommandParseException;

    String getHelpMessage();
    String getUsageMessage();
}
