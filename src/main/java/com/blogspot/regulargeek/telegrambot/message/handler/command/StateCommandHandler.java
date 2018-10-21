package com.blogspot.regulargeek.telegrambot.message.handler.command;

import com.blogspot.regulargeek.telegrambot.message.commands.OnCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.StateCommand;
import com.blogspot.regulargeek.telegrambot.message.handler.TelegramCommandHandler;
import model.ItemDTO;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
public class StateCommandHandler extends TelegramCommandHandler<StateCommand> {


    @Override
    public void handle(StateCommand command) {
        SendMessage message = command.getSendMessage();
        String itemName = command.getItemName();
        ItemDTO itemDTO = restApiService.getItem(itemName);
        message.setText(prepareResponse(itemDTO));
        sendResponse(message);
    }

    private String prepareResponse(ItemDTO itemDTO) {
        StringBuilder builder = new StringBuilder();
        builder.append("<b>");
        builder.append(itemDTO.getName());
        builder.append("</b> = <b>");
        builder.append(itemDTO.getState());
        builder.append("</b>");
        return builder.toString();
    }

}
