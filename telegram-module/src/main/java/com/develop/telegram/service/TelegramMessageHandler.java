package com.develop.telegram.service;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramMessageHandler {

    public SendMessage processTelegramMessage(Update update);
}
