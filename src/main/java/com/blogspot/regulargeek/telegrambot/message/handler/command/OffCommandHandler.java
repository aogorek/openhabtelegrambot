package com.blogspot.regulargeek.telegrambot.message.handler.command;

import com.blogspot.regulargeek.telegrambot.message.commands.OffCommand;
import com.blogspot.regulargeek.telegrambot.message.handler.TelegramCommandHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
public class OffCommandHandler extends TelegramCommandHandler<OffCommand> {

    private static final String COMMAND = "OFF";

    @Override
    public void handle(OffCommand command) {
        SendMessage message = command.getSendMessage();
        String itemName = command.getItemName();
        Object object = restApiService.sendCommand(itemName, COMMAND);
        message.setText(prepareResponse(object));
        sendResponse(message);
    }

    private String prepareResponse(Object result) {
        StringBuilder builder = new StringBuilder();
        builder.append("Command execuded.");
        if (result != null) {
            builder.append("\n");
            builder.append(result.toString());
        }
        return builder.toString();
    }

}
