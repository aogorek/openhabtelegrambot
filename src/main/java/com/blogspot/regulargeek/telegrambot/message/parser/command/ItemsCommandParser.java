package com.blogspot.regulargeek.telegrambot.message.parser.command;

import com.blogspot.regulargeek.telegrambot.annotations.SupportedCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.ItemsCommand;
import com.blogspot.regulargeek.telegrambot.message.parser.SingleCommandParser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
@SupportedCommand(command = "ITEMS")
public class ItemsCommandParser implements SingleCommandParser {
    public ItemsCommand parse(SendMessage message) {
        ItemsCommand command = new ItemsCommand(message);
        return command;
    }

    @Override
    public String getHelpMessage() {
        return "Returns OpenHab items list.";
    }

    @Override
    public String getUsageMessage() {
        return "Takes no arguments. Returns OpenHab items list.";
    }
}
