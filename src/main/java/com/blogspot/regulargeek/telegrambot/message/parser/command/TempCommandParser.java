package com.blogspot.regulargeek.telegrambot.message.parser.command;

import com.blogspot.regulargeek.telegrambot.annotations.SupportedCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.SwitchesCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.TempCommand;
import com.blogspot.regulargeek.telegrambot.message.parser.SingleCommandParser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
@SupportedCommand(command = "TEMP")
public class TempCommandParser implements SingleCommandParser {
    public TempCommand parse(SendMessage message) {
        TempCommand command = new TempCommand(message);
        return command;
    }

    @Override
    public String getHelpMessage() {
        return "Returns OpenHab TEMPERATURE values list.";
    }

    @Override
    public String getUsageMessage() {
        return "Takes no arguments. Returns OpenHab TEMPERATURE values list.";
    }
}
