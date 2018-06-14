package com.blogspot.regulargeek.telegrambot.message.handler.command;

import com.blogspot.regulargeek.telegrambot.message.commands.ItemsCommand;
import com.blogspot.regulargeek.telegrambot.message.handler.TelegramCommandHandler;
import model.ItemDTO;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.List;

@Service
public class ItemsCommandHandler extends TelegramCommandHandler<ItemsCommand> {

    @Override
    public void handle(ItemsCommand command) {
        List<ItemDTO> items = restApiService.getItems();
        SendMessage message = command.getSendMessage();
        message.setText(prepareResponse(items));
        sendResponse(message);
    }

    private String prepareResponse(List<ItemDTO> items) {
        StringBuilder builder = new StringBuilder();
        items.stream().forEach(itemDTO ->
                builder.append(itemDTO.getName())
                        .append(" - ")
                        .append(itemDTO.getLabel())
                        .append("\n"));
        return builder.toString();
    }

}
