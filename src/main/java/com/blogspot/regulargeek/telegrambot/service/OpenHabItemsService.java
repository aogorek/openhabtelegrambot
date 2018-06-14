package com.blogspot.regulargeek.telegrambot.service;

import model.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenHabItemsService {

    private Map<String, ItemDTO> itemList = null;

    @Autowired
    private RestApiService restApiService;

    public ItemDTO getItem(String itemName) {
        if (itemList == null) {
           refreshItemList();
        }
        return itemList.get(itemName);
    }

    private void refreshItemList() {
        itemList = new HashMap<>();
        List<ItemDTO> items = restApiService.getItems();
        items.stream().forEach(itemDTO -> itemList.put(itemDTO.getName(), itemDTO));
    }


    private String prepareResponse(Object result) {
        StringBuilder builder = new StringBuilder();
        builder.append(result.toString());
        return builder.toString();
    }



}
