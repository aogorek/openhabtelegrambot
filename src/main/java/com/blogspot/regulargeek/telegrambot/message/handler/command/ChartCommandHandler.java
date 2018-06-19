package com.blogspot.regulargeek.telegrambot.message.handler.command;

import com.blogspot.regulargeek.telegrambot.message.commands.ChartCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.StateCommand;
import com.blogspot.regulargeek.telegrambot.message.handler.TelegramCommandHandler;
import model.ItemDTO;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ChartCommandHandler extends TelegramCommandHandler<ChartCommand> {

    @Override
    public void handle(ChartCommand command) {
        SendMessage message = command.getSendMessage();
        SendPhoto photo = new SendPhoto();
        photo.setChatId(message.getChatId());
        photo.setCaption(command.getItemName());
        InputStream photoStream = null;
        try {
            photoStream = restApiService.getChartStream(command.getItemName());
            photo.setNewPhoto(command.getItemName(), photoStream);
            sendResponse(photo);
            photoStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
