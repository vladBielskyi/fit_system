package com.develop.telegram.bot.markup.impl;

import com.develop.telegram.bot.markup.ReplyKeyboardMarkupItem;
import com.library.commons.enums.BotState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ShowMainMenuKeyboardMarkup implements ReplyKeyboardMarkupItem {

    private static final String START_WORKOUT_BUTTON = "Почати тренування";
    private static final String ADD_WORKOUT_BUTTON = "Додати тренування";
    private static final String MY_TRAINING_BUTTON = "Мої тренування";

    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private final Map<String, BotState> nextBotState = Map.of(
            START_WORKOUT_BUTTON, BotState.SHOW_WORKOUT_MENU,
            ADD_WORKOUT_BUTTON, BotState.SHOW_ADD_WORKOUT_MENU,
            MY_TRAINING_BUTTON, BotState.SHOW_MY_TRAINING_MENU
    );

    @Override
    public BotState getBotState() {
        return BotState.SHOW_MAIN_MENU;
    }

    @Override
    public ReplyKeyboardMarkup getReplyKeyboardMarkup(Long userId) {
        return replyKeyboardMarkup;
    }

    @Override
    public Map<String, BotState> getNextBotState(Long userId) {
        return nextBotState;
    }

    @PostConstruct
    private void initReplyKeyboardMarkup() {
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow upperButtons = new KeyboardRow();
        upperButtons.add(START_WORKOUT_BUTTON);
        upperButtons.add(ADD_WORKOUT_BUTTON);
        rows.add(upperButtons);
        KeyboardRow bottomButtons = new KeyboardRow();
        bottomButtons.add(MY_TRAINING_BUTTON);
        rows.add(bottomButtons);
        this.replyKeyboardMarkup = new ReplyKeyboardMarkup(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);
    }
}
