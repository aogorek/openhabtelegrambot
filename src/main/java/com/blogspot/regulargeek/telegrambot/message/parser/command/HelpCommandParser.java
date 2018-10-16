package com.blogspot.regulargeek.telegrambot.message.parser.command;

import com.blogspot.regulargeek.telegrambot.annotations.SupportedCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.HelpCommand;
import com.blogspot.regulargeek.telegrambot.message.parser.MessageParser;
import com.blogspot.regulargeek.telegrambot.message.parser.SingleCommandParser;
import com.blogspot.regulargeek.telegrambot.exception.CommandParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
@SupportedCommand(command = "HELP")
public class HelpCommandParser implements SingleCommandParser {
    private static final int COMMAND_NAME_POSITION = 1;

    @Autowired
    private MessageParser messageParser;

    public HelpCommand parse(SendMessage message) throws CommandParseException {
        String[] parts = message.getText().split(" ");
        validateCommand(parts);
        HelpCommand command = new HelpCommand(message, parts.length>1?parts[COMMAND_NAME_POSITION]:null);
        return command;
    }

    private void validateCommand(String[] parts) throws CommandParseException {
        if (parts.length > 1) {

            String commandName = parts[COMMAND_NAME_POSITION].toUpperCase();

            if (!messageParser.isCommandSupported(commandName)) {
                throw new CommandParseException("Comand not found. Type 'HELP' to get list of supported commands.");
            }
        }

    }

    @Override
    public String getHelpMessage() {
        return "Returns help.";
    }

    @Override
    public String getUsageMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("Return help for OpenHab bot.")
                .append("\n\n")
                .append("Usage: ")
                .append("\n")
                .append("HELP - for message list")
                .append("\n")
                .append("<b>HELP</b> <i>{command_name}</i> - for help to specific message");
        return builder.toString();
    }
}
