package com.blogspot.regulargeek.telegrambot.service;

import model.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class MeteoApiService {
    @Value("${meteoURL}")
    private String meteoURL;

    @Autowired
    private ApplicationContext context;

    public InputStream getMeteoStream() throws IOException {
        Resource resource= context.getResource(meteoURL);
        return resource.getInputStream();
    }
}
