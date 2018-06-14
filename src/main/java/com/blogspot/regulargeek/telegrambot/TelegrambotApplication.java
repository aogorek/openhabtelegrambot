package com.blogspot.regulargeek.telegrambot;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class TelegrambotApplication {

    public static void main(String[] args) {

        ApiContextInitializer.init();

        new SpringApplicationBuilder(TelegrambotApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }



}
