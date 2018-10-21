package com.blogspot.regulargeek.telegrambot.service;

import com.blogspot.regulargeek.telegrambot.event.TelegramCommandReceivedEvent;
import com.blogspot.regulargeek.telegrambot.event.TelegramCommandResponseReadyEvent;
import com.blogspot.regulargeek.telegrambot.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

@Service
public class TelegramBot extends TelegramLongPollingBot implements ApplicationListener<TelegramCommandResponseReadyEvent> {

    @Value("${botUserName}")
    private String botUserName;

    @Value("${botToken}")
    private String botToken;

    @Value("${allowedChatIDs}")
    private String allowedChatIDs;

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
            return;
        }
        if (update.getCallbackQuery() != null) {
            handleCallBack(update.getCallbackQuery());
        }
    }

    private void handleCallBack(CallbackQuery callbackQuery) {
        Message sourceMessage = callbackQuery.getMessage();
	if (allowedChatIDs.equals("") || allowedChatIDs.equals("*") || Arrays.asList(allowedChatIDs.split(",")).contains(Long.toString(sourceMessage.getChatId()))) {
		SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
		        .setChatId(sourceMessage.getChatId())
		        .setText(callbackQuery.getData())
		        .enableHtml(true);
		TelegramCommandReceivedEvent event = new TelegramCommandReceivedEvent(message);
		applicationEventPublisher.publishEvent(event);
	}
    }

    private void handlePost(Message sourceMessage) {
	if (allowedChatIDs.equals("") || allowedChatIDs.equals("*") || Arrays.asList(allowedChatIDs.split(",")).contains(Long.toString(sourceMessage.getChatId()))) {
		SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
		        .setChatId(sourceMessage.getChatId())
		        .setText(sourceMessage.getText())
		        .enableHtml(true);
		TelegramCommandReceivedEvent event = new TelegramCommandReceivedEvent(message);
		applicationEventPublisher.publishEvent(event);
	}
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
        PartialBotApiMethod method = (PartialBotApiMethod) telegramCommandResponseReadyEvent.getSource();
        if (method instanceof SendMessage) {
            handleTextMessage(method);
            return;
        }

        if (method instanceof SendPhoto) {
            try {
                handlePhoto(method);
                return;
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        throw new RuntimeException("Not supported Telegram message type");

    }

    private void handlePhoto(PartialBotApiMethod method) throws TelegramApiException {
        sendPhoto((SendPhoto) method);
    }

    private void handleTextMessage(PartialBotApiMethod method) {
        SendMessage message = (SendMessage)method;
        List<String> splittedMessages = MessageUtil.splitEqually(message.getText(), MessageUtil.MAX_MESSAGE_LENGTH);
        splittedMessages.stream().forEach(s -> {
            message.setText(s);
            message.enableHtml(true);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }
}
