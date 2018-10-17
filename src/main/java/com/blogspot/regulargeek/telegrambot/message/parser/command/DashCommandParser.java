package com.blogspot.regulargeek.telegrambot.message.parser.command;

import com.blogspot.regulargeek.telegrambot.annotations.SupportedCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.DashCommand;
import com.blogspot.regulargeek.telegrambot.message.parser.SingleCommandParser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
@SupportedCommand(command = "DASH")
public class DashCommandParser implements SingleCommandParser {
    public DashCommand parse(SendMessage message) {
        DashCommand command = new DashCommand(message);
        return command;
    }

    @Override
    public String getHelpMessage() {
        return "Returns dashboard with OpenHab items and their states.";
    }

    @Override
    public String getUsageMessage() {
        return "Takes no arguments. Returns dashboard containing OpenHab items/states list.";
    }
}
