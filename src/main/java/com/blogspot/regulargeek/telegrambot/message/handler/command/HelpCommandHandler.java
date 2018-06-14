package com.blogspot.regulargeek.telegrambot.message.handler.command;

import com.blogspot.regulargeek.telegrambot.message.commands.HelpCommand;
import com.blogspot.regulargeek.telegrambot.message.handler.TelegramCommandHandler;
import com.blogspot.regulargeek.telegrambot.message.parser.MessageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.List;

@Service
public class HelpCommandHandler extends TelegramCommandHandler<HelpCommand> {

    @Autowired
    private MessageParser messageParser;

    @Override
    public void handle(HelpCommand command) {
        SendMessage message = command.getSendMessage();
        if (command.getCommandName() != null) {
            message.setText(messageParser.getParserUsage(command.getCommandName()));
        } else {
            message.setText(prepareResponse(messageParser.getParserHelpMessages()));
        }

        sendResponse(message);
    }

    private String prepareResponse(List<String> helpMessages) {
        StringBuilder builder = new StringBuilder();
        helpMessages.stream().forEach(message ->
                builder.append(message)
                        .append("\n"));
        return builder.toString();
    }

}
