package com.develop.telegram.bot.markup.factory;

import com.develop.telegram.bot.markup.ReplyKeyboardMarkupItem;
import com.library.commons.enums.BotState;
import com.develop.telegram.exceptions.UnsupportedBotStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyKeyboardMarkupFactory {

    private final List<ReplyKeyboardMarkupItem> replyKeyboardMarkupItems;

    public ReplyKeyboardMarkupItem getReplyKeyboardMarkupItem(BotState botState) {
        return replyKeyboardMarkupItems.stream()
                .filter(x -> x.getBotState() == botState)
                .findFirst()
                .orElseThrow(() -> new UnsupportedBotStateException(
                        String.format("Unsupported Bot State: %s", botState.name())));
    }
}
