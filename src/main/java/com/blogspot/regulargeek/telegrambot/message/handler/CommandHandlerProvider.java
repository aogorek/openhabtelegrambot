package com.blogspot.regulargeek.telegrambot.message.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommandHandlerProvider implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    private Map<Class<?>, String> handlers = new HashMap<Class<?>, String>();

    public TelegramCommandHandler getHandler(Object command) {
        String beanName = handlers.get(command.getClass());
        if (beanName == null) {
            throw new RuntimeException("message handler not found. Command class is " + command.getClass());
        }
        TelegramCommandHandler handler = beanFactory.getBean(beanName, TelegramCommandHandler.class);
        return handler;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        handlers.clear();
        String[] commandHandlersNames = beanFactory.getBeanNamesForType(TelegramCommandHandler.class);
        for (String beanName : commandHandlersNames) {
            BeanDefinition commandHandler = beanFactory.getBeanDefinition(beanName);
            try {
                Class<?> handlerClass = Class.forName(commandHandler.getBeanClassName());
                handlers.put(getHandledCommandType(handlerClass), beanName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Class<?> getHandledCommandType(Class<?> clazz) {
        Type genericClass = clazz.getGenericSuperclass();
        ParameterizedType type = findByRawType(genericClass, TelegramCommandHandler.class);
        return (Class<?>) type.getActualTypeArguments()[0];
    }

    private ParameterizedType findByRawType(Type genericClass, Class<?> expectedRawType) {
            if (genericClass instanceof ParameterizedType) {
                ParameterizedType parametrized = (ParameterizedType) genericClass;
                if (expectedRawType.equals(parametrized.getRawType())) {
                    return parametrized;
                }
            }
        throw new RuntimeException();
    }


}
