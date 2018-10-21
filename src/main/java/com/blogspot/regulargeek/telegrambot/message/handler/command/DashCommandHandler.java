package com.blogspot.regulargeek.telegrambot.message.handler.command;

import com.blogspot.regulargeek.telegrambot.message.commands.DashCommand;
import com.blogspot.regulargeek.telegrambot.message.handler.TelegramCommandHandler;
import model.ItemDTO;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.springframework.beans.factory.annotation.Value;

import java.util.Iterator;
import java.util.List;

@Service
public class DashCommandHandler extends TelegramCommandHandler<DashCommand> {
    @Value("${dashItems}")
    private String dashItems;

    private boolean isDashItem(String itemName) {
        if (dashItems.equals("")) { return false; }
        if (dashItems.equals("*")) { return true; }
        String[] arrayDashItems = dashItems.split(",");
        for (String s: arrayDashItems) {
            if (itemName.equals(s) || itemName.matches(s)) { return true; }
        }
        return false;
    }

    @Override
    public void handle(DashCommand command) {
        List<ItemDTO> items = restApiService.getItems();
        SendMessage message = command.getSendMessage();
        message.setText(prepareResponse(items));
        sendResponse(message);
    }

    private String prepareResponse(List<ItemDTO> items) {
	Iterator<ItemDTO> i = items.iterator();
        while (i.hasNext()) {
            ItemDTO o = i.next();
            if (!isDashItem(o.getName())) { i.remove(); }
        }

	StringBuilder builder = new StringBuilder();
        items.stream().forEach(itemDTO ->
                builder.append("<b>")
                        .append(itemDTO.getName())
                        .append("</b> - ")
                        .append(itemDTO.getLabel())
			.append("\n--> <i>")
			.append(itemDTO.getState())
                        .append("</i>\n\n")
	);
        return builder.toString();
    }

}
