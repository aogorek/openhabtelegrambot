package com.blogspot.regulargeek.telegrambot.message.handler.command;

import com.blogspot.regulargeek.telegrambot.message.commands.TempCommand;
import com.blogspot.regulargeek.telegrambot.message.handler.TelegramCommandHandler;
import model.ItemDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TempCommandHandler extends TelegramCommandHandler<TempCommand> {

    private static final String TEMPERATURE = "temperature";

    @Override
    public void handle(TempCommand command) {
        List<ItemDTO> items = restApiService.getItems().stream().filter(itemDTO -> TEMPERATURE.equals(itemDTO.getCategory())).collect(Collectors.toList());
        SendMessage message = command.getSendMessage();

        message.setReplyMarkup(prepareResponseButtons(items));
        sendResponse(message);
    }

    InlineKeyboardMarkup prepareResponseButtons(List<ItemDTO> items) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        for (int a = 0; a < items.size(); a++) {
            ItemDTO itemDTO = items.get(a);
            String txt = StringUtils.isEmpty(itemDTO.getLabel()) ? itemDTO.getName() : itemDTO.getLabel();
            txt += "  " + itemDTO.getState();
            rowInline.add(new InlineKeyboardButton().setText(txt).setCallbackData("CHART " + itemDTO.getName()));
            if (a % 2 == 0) {
                rowsInline.add(rowInline);
                rowInline = new ArrayList<>();
            }
        }
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

}
