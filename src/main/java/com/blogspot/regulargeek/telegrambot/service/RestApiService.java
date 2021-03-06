package com.blogspot.regulargeek.telegrambot.service;

import model.ItemDTO;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

@Service
public class RestApiService {
    @Value("${openHabURL}")
    private String openHabURL;

    @Value("${allowedItems}")
    private String allowedItems;

    @Autowired
    private ApplicationContext context;

    private boolean isAllowedItem(String itemName) {
        if (allowedItems.equals("") || allowedItems.equals("*")) { return true; }
        String[] arrayAllowedItems = allowedItems.split(",");
        for (String s: arrayAllowedItems) {
            if (itemName.equals(s) || itemName.matches(s)) { return true; }
        }
        return false;
    }

    public Object sendCommand(String item, String command) {
        if (isAllowedItem(item)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> request = new HttpEntity<>(command);
            String requestUrl = openHabURL + "/rest/items/" + item;
            Object result = restTemplate.postForObject(requestUrl, request, String.class );
            return result;
        }
        return null;
    }

    public List<ItemDTO> getItems() {
        RestTemplate restTemplate = new RestTemplate();

        String requestUrl = openHabURL + "/rest/items?recursive=false";
        ResponseEntity<List<ItemDTO>> tmp = restTemplate.exchange(requestUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ItemDTO>>() {});
        List<ItemDTO> result = tmp.getBody();
	Iterator<ItemDTO> i = result.iterator();
        while (i.hasNext()) {
            ItemDTO o = i.next();
            if (!isAllowedItem(o.getName())) { i.remove(); }
        }
        return result;
    }


    public ItemDTO getItem(String itemName) {
        if (isAllowedItem(itemName)) {
            RestTemplate restTemplate = new RestTemplate();
            String requestUrl = openHabURL + "/rest/items/" + itemName;
            ResponseEntity<ItemDTO> result = restTemplate.exchange(requestUrl, HttpMethod.GET, null, new ParameterizedTypeReference<ItemDTO>() {});
            return result.getBody();
        }
        return null;
    }

    public InputStream getChartStream(String itemName) throws IOException {
        if (isAllowedItem(itemName)) {
            String requestUrl = "url:" + openHabURL + "/chart?items=" + itemName;
            Resource resource= context.getResource(requestUrl);
            return resource.getInputStream();
        }
        return null;
    }
}
