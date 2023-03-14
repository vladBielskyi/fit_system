package com.develop.telegram.bot.markup;

import com.library.commons.enums.BotState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Map;

public interface ReplyKeyboardMarkupItem {

    public BotState getBotState();

    public ReplyKeyboardMarkup getReplyKeyboardMarkup(Long userId);

    public Map<String, BotState> getNextBotState(Long userId);

}
