package com.blogspot.regulargeek.telegrambot.service;

import model.ItemDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestApiService {
    @Value("${openHabURL}")
    private String openHabURL;


    public Object sendCommand(String item, String command) {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(command);
        String requestUrl = openHabURL + "/rest/items/" + item;
        Object result = restTemplate.postForObject(requestUrl, request, String.class );
        return result;

    }

    public List<ItemDTO> getItems() {
        RestTemplate restTemplate = new RestTemplate();

        String requestUrl = openHabURL + "/rest/items?recursive=false";
        ResponseEntity<List<ItemDTO>> result = restTemplate.exchange(requestUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ItemDTO>>() {});
        return result.getBody();
    }




}
