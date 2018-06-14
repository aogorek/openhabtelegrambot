package com.blogspot.regulargeek.telegrambot.event;

import com.blogspot.regulargeek.telegrambot.message.TelegramCommand;
import com.blogspot.regulargeek.telegrambot.message.handler.CommandHandlerProvider;
import com.blogspot.regulargeek.telegrambot.message.handler.TelegramCommandHandler;
import com.blogspot.regulargeek.telegrambot.message.parser.MessageParser;
import com.blogspot.regulargeek.telegrambot.exception.CommandParseException;
import com.blogspot.regulargeek.telegrambot.exception.InvalidCommandException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
public class TelegramCommandReceivedEventHandler  implements ApplicationListener<TelegramCommandReceivedEvent> {

    @Autowired
    private MessageParser messageParser;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private CommandHandlerProvider commandHandlerProvider;

    @Override
    public void onApplicationEvent(TelegramCommandReceivedEvent telegramCommandReceivedEvent) {
        SendMessage  sendMessage = (SendMessage) telegramCommandReceivedEvent.getSource();
        try {
            TelegramCommand command = messageParser.parse(sendMessage);
            TelegramCommandHandler commandHandler = commandHandlerProvider.getHandler(command);
            commandHandler.handle(command);
        } catch (InvalidCommandException e) {
            e.printStackTrace();
            sendErrorResponse(sendMessage, "Unrecognized message. Type HELP for list of supported commands");
        } catch (CommandParseException e) {
            e.printStackTrace();
            sendErrorResponse(sendMessage, e.getMessage());
        }
    }

    private void sendErrorResponse(SendMessage sendMessage, String text) {
        sendMessage.setText(text);
        TelegramCommandResponseReadyEvent response = new TelegramCommandResponseReadyEvent(sendMessage);
        applicationEventPublisher.publishEvent(response);
    }
}
