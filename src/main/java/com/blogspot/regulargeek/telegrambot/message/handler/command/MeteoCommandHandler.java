package com.blogspot.regulargeek.telegrambot.message.handler.command;

import com.blogspot.regulargeek.telegrambot.message.commands.ChartCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.MeteoCommand;
import com.blogspot.regulargeek.telegrambot.message.handler.TelegramCommandHandler;
import com.blogspot.regulargeek.telegrambot.service.MeteoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

import java.io.IOException;
import java.io.InputStream;

@Service
public class MeteoCommandHandler extends TelegramCommandHandler<MeteoCommand> {

    @Autowired
    private MeteoApiService meteoApiService;

    @Override
    public void handle(MeteoCommand command) {
        SendMessage message = command.getSendMessage();
        SendPhoto photo = new SendPhoto();
        photo.setChatId(message.getChatId());
        InputStream photoStream = null;
        try {
            photoStream = meteoApiService.getMeteoStream();
            photo.setNewPhoto("Meteo", photoStream);
            sendResponse(photo);
            photoStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
