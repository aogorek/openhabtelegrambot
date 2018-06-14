package com.blogspot.regulargeek.telegrambot.message.parser.command;

import com.blogspot.regulargeek.telegrambot.annotations.SupportedCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.OffCommand;
import com.blogspot.regulargeek.telegrambot.message.parser.SingleCommandParser;
import com.blogspot.regulargeek.telegrambot.exception.CommandParseException;
import com.blogspot.regulargeek.telegrambot.service.OpenHabItemsService;
import model.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
@SupportedCommand(command = "OFF")
public class OffCommandParser implements SingleCommandParser {
    private static final int ITEM_NAME_POSITION = 1;

    @Autowired
    private OpenHabItemsService openHabItemsService;

    public OffCommand parse(SendMessage message) throws CommandParseException {

        String[] parts = message.getText().split(" ");
        validateCommand(parts);
        String itemName = parts[ITEM_NAME_POSITION];
        OffCommand command = new OffCommand(message, itemName);
        return command;
    }

    private void validateCommand(String[] parts) throws CommandParseException {
        if (parts.length != 2) {
            throw new CommandParseException("Invalid message syntax. Type 'HELP OFF' to get proper syntax.");
        }
        String itemName = parts[ITEM_NAME_POSITION];
        ItemDTO dto = openHabItemsService.getItem(itemName);

        if (dto == null) {
            throw new CommandParseException("Item not found. Type 'ITEMS' to get list of known items. If item is not visible and is present in OpenHab type 'REFRESH' to reload items.");
        }

    }

    @Override
    public String getHelpMessage() {
        return "Turns OFF specified ITEM.";
    }

    @Override
    public String getUsageMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("Turns OFF specified ITEM.")
                .append("\n")
                .append("Usage: ")
                .append("\n")
                .append("OFF {item_name}");
        return builder.toString();
    }
}
