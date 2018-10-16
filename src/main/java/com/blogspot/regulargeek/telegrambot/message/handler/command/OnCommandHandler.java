package com.blogspot.regulargeek.telegrambot.message.handler.command;

import com.blogspot.regulargeek.telegrambot.message.commands.OnCommand;
import com.blogspot.regulargeek.telegrambot.message.handler.TelegramCommandHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
public class OnCommandHandler extends TelegramCommandHandler<OnCommand> {

    private static final String COMMAND = "ON";

    @Override
    public void handle(OnCommand command) {
        SendMessage message = command.getSendMessage();
        String itemName = command.getItemName();
        Object object = restApiService.sendCommand(itemName, COMMAND);
        message.setText(prepareResponse(object));
        sendResponse(message);
    }

    private String prepareResponse(Object result) {
        StringBuilder builder = new StringBuilder();
        builder.append("Command executed.\n");
        if (result != null) {
            builder.append("\n");
            builder.append(result.toString());
        }
        return builder.toString();
    }

}
