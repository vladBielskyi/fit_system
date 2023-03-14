package com.develop.telegram.bot;

import com.develop.telegram.service.TelegramMessageHandler;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final String botToken;
    private final String username;

    private final TelegramMessageHandler telegramMessageHandler;

    public TelegramBot(@Value("${telegram.bot.token}") String botToken,
                       @Value("${telegram.bot.username}") String username,
                       TelegramMessageHandler telegramMessageHandler) {
        super(botToken);
        this.botToken = botToken;
        this.username = username;
        this.telegramMessageHandler = telegramMessageHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(telegramMessageHandler.processTelegramMessage(update));
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

}
