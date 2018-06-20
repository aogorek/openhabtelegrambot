package com.blogspot.regulargeek.telegrambot.message.handler.command;

import com.blogspot.regulargeek.telegrambot.message.commands.ItemsCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.SwitchesCommand;
import com.blogspot.regulargeek.telegrambot.message.handler.TelegramCommandHandler;
import model.ItemDTO;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SwitchesCommandHandler extends TelegramCommandHandler<SwitchesCommand> {

    private static final String SWITCH = "Switch";

    @Override
    public void handle(SwitchesCommand command) {
        List<ItemDTO> items = restApiService.getItems().stream().filter(itemDTO -> SWITCH.equals(itemDTO.getType())).collect(Collectors.toList());
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
                        .append(" - ")
                        .append(itemDTO.getState())
                        .append("\n"));
        return builder.toString();
    }

}
