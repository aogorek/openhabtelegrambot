package com.blogspot.regulargeek.telegrambot.message.parser.command;

import com.blogspot.regulargeek.telegrambot.annotations.SupportedCommand;
import com.blogspot.regulargeek.telegrambot.exception.CommandParseException;
import com.blogspot.regulargeek.telegrambot.message.commands.ChartCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.MeteoCommand;
import com.blogspot.regulargeek.telegrambot.message.commands.TempCommand;
import com.blogspot.regulargeek.telegrambot.message.parser.SingleCommandParser;
import com.blogspot.regulargeek.telegrambot.service.OpenHabItemsService;
import model.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
@SupportedCommand(command = "METEO")
public class MeteoCommandParser implements SingleCommandParser {
    public MeteoCommand parse(SendMessage message) {
        MeteoCommand command = new MeteoCommand(message);
        return command;
    }

    @Override
    public String getHelpMessage() {
        return "Returns Meteo info from icm.edu.pl meteo specified URL";
    }

    @Override
    public String getUsageMessage() {
        return "Takes no arguments. Returns Meteo info from icm.edu.pl meteo specified URL";
    }

}
