package com.blogspot.regulargeek.telegrambot.message.parser.command;

import com.blogspot.regulargeek.telegrambot.annotations.SupportedCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.ItemsCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.SwitchesCommand;
import com.blogspot.regulargeek.telegrambot.message.parser.SingleCommandParser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
@SupportedCommand(command = "SWITCHES")
public class SwitchesCommandParser implements SingleCommandParser {
    public SwitchesCommand parse(SendMessage message) {
        SwitchesCommand command = new SwitchesCommand(message);
        return command;
    }

    @Override
    public String getHelpMessage() {
        return "Returns OpenHab SWITCHES list.";
    }

    @Override
    public String getUsageMessage() {
        return "Takes no arguments. Returns OpenHab SWITCHES list.";
    }
}
