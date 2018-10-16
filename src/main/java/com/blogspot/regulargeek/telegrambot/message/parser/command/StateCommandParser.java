package com.blogspot.regulargeek.telegrambot.message.parser.command;

import com.blogspot.regulargeek.telegrambot.annotations.SupportedCommand;
import com.blogspot.regulargeek.telegrambot.exception.CommandParseException;
import com.blogspot.regulargeek.telegrambot.message.commands.ItemsCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.OnCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.StateCommand;
import com.blogspot.regulargeek.telegrambot.message.parser.SingleCommandParser;
import com.blogspot.regulargeek.telegrambot.service.OpenHabItemsService;
import model.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
@SupportedCommand(command = "STATE")
public class StateCommandParser implements SingleCommandParser {
    private static final int ITEM_NAME_POSITION = 1;

    @Autowired
    private OpenHabItemsService openHabItemsService;

    public StateCommand parse(SendMessage message) throws CommandParseException {

        String[] parts = message.getText().split(" ");
        validateCommand(parts);
        String itemName = parts[ITEM_NAME_POSITION];
        StateCommand command = new StateCommand(message, itemName);
        return command;
    }

    private void validateCommand(String[] parts) throws CommandParseException {
        if (parts.length != 2) {
            throw new CommandParseException("<b>Invalid message syntax</b>\nType <b>HELP STATE</b> to get proper syntax");
        }
        String itemName = parts[ITEM_NAME_POSITION];
        ItemDTO dto = openHabItemsService.getItem(itemName);

        if (dto == null) {
            throw new CommandParseException("Item not found. Type 'ITEMS' to get list of known items. If item is not visible and is present in OpenHab type 'REFRESH' to reload items.");
        }

    }

    @Override
    public String getHelpMessage() {
        return "Returns STATE of  specified ITEM.";
    }

    @Override
    public String getUsageMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("Returns <b>STATE</b> of specified <b>ITEM</b>.")
                .append("\n\n")
                .append("Usage: ")
                .append("\n")
                .append("<b>STATE</b> <i>{item_name}</i>");
        return builder.toString();
    }
}
