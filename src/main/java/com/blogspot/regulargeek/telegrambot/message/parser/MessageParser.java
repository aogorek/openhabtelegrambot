package com.blogspot.regulargeek.telegrambot.message.parser;

import com.blogspot.regulargeek.telegrambot.annotations.SupportedCommand;
import com.blogspot.regulargeek.telegrambot.message.TelegramCommand;
import com.blogspot.regulargeek.telegrambot.exception.CommandParseException;
import com.blogspot.regulargeek.telegrambot.exception.InvalidCommandException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageParser implements ApplicationListener<ContextRefreshedEvent> {

    private Map<String, Class<? extends SingleCommandParser>> singleCommandParsers = new HashMap<>();

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    public TelegramCommand parse(SendMessage message) throws InvalidCommandException, CommandParseException {
        String msgText = message.getText();
        SingleCommandParser specificParser = findSpecificParser(msgText);
        return specificParser.parse(message);
    }

    private SingleCommandParser findSpecificParser(String msgText) throws InvalidCommandException {
        String[] args = msgText.split(" ");
        if (args.length > 0) {
            String sentCommand = args[0].toUpperCase();


            SingleCommandParser handlerClass = findParserForCommand(sentCommand);
            if (handlerClass != null) {
                return handlerClass;
            }
        }
        throw new InvalidCommandException("Unknown message");
    }

    private SingleCommandParser findParserForCommand(String sentCommand) {
        Class<? extends SingleCommandParser> handlerClass = singleCommandParsers.get(sentCommand);
        if (handlerClass != null) {
            SingleCommandParser parser = beanFactory.getBean(handlerClass);
            return parser;
        }
        return null;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        singleCommandParsers.clear();
        String[] commandParsersNames = beanFactory.getBeanNamesForType(SingleCommandParser.class);
        for (String beanName : commandParsersNames) {
            BeanDefinition singleCommandParser = beanFactory.getBeanDefinition(beanName);
            try {
                Class<? extends SingleCommandParser> handlerClass = (Class<? extends SingleCommandParser>) Class.forName(singleCommandParser.getBeanClassName());
                singleCommandParsers.put(getHandledCommandType(handlerClass), handlerClass);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getHandledCommandType(Class<?> clazz) {
        SupportedCommand command = clazz.getAnnotation(SupportedCommand.class);
        if (command == null) {
            throw new RuntimeException("Class " + clazz + " should be annotaded with SupportedCommand annotation");
        }
        return command.command();
    }

    public List<String> getParserHelpMessages() {
        List<String> helpMessages = new ArrayList<>();
        singleCommandParsers.values().stream().forEach(parser -> {
            if (parser != null) {
                SingleCommandParser parserBean = beanFactory.getBean(parser);
                SupportedCommand command = parser.getAnnotation(SupportedCommand.class);
                helpMessages.add("<b>" + command.command() + "</b>\n" + parserBean.getHelpMessage() + "\n");
            }
        });
        return helpMessages;
    }

    public String getParserUsage(String commandName) {
        SingleCommandParser parser = findParserForCommand(commandName);
        if (parser != null) {
            return "<b>" + commandName + "</b> - " + parser.getUsageMessage();
        }

        return "Command not supported.";
    }


    public boolean isCommandSupported(String commandName) {
        return findParserForCommand(commandName) != null;
    }
}
