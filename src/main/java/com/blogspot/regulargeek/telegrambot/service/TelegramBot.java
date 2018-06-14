package com.blogspot.regulargeek.telegrambot.service;

import com.blogspot.regulargeek.telegrambot.event.TelegramCommandReceivedEvent;
import com.blogspot.regulargeek.telegrambot.event.TelegramCommandResponseReadyEvent;
import com.blogspot.regulargeek.telegrambot.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

@Service
public class TelegramBot extends TelegramLongPollingBot implements ApplicationListener<TelegramCommandResponseReadyEvent> {

    @Value("${botUserName}")
    private String botUserName;

    @Value("${botToken}")
    private String botToken;

    @Autowired
    RestApiService restApiService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text

        if (update.hasChannelPost() && update.getChannelPost().hasText()) {
            handlePost(update.getChannelPost());
            return;
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            handlePost(update.getMessage());
        }
    }

    private void handlePost(Message sourceMessage) {
        SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(sourceMessage.getChatId())
                .setText(sourceMessage.getText());
        TelegramCommandReceivedEvent event = new TelegramCommandReceivedEvent(message);
        applicationEventPublisher.publishEvent(event);
    }


    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onApplicationEvent(TelegramCommandResponseReadyEvent telegramCommandResponseReadyEvent) {
        SendMessage message = (SendMessage) telegramCommandResponseReadyEvent.getSource();
        List<String> splittedMessages = MessageUtil.splitEqually(message.getText(), MessageUtil.MAX_MESSAGE_LENGTH);
        splittedMessages.stream().forEach(s -> {
            message.setText(s);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });

    }
}
