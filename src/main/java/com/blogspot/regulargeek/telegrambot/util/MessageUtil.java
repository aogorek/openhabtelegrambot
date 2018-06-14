package com.blogspot.regulargeek.telegrambot.util;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil {

    public static final int MAX_MESSAGE_LENGTH = 4096;

    public static List<String> splitEqually(String text, int size) {
        List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }
}
