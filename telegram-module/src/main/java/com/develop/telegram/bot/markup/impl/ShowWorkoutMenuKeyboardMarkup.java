package com.develop.telegram.bot.markup.impl;

import com.develop.telegram.bot.markup.ReplyKeyboardMarkupItem;
import com.library.commons.enums.BotState;
import com.develop.telegram.exceptions.NotAddedWorkoutsException;
import com.develop.telegram.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@Component
@RequiredArgsConstructor
public class ShowWorkoutMenuKeyboardMarkup implements ReplyKeyboardMarkupItem {

    private static final String MAIN_MENU_BUTTON = "Головне меню";

    private final WorkoutService workoutService;

    @Override
    public BotState getBotState() {
        return BotState.SHOW_WORKOUT_MENU;
    }

    @Override
    public ReplyKeyboardMarkup getReplyKeyboardMarkup(Long userId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow buttons = new KeyboardRow();
        List<String> workouts = getWorkoutsName(userId);

        for (String workout : workouts) {
            buttons.add(workout);
        }

        KeyboardRow mainMenuButton = new KeyboardRow();
        mainMenuButton.add(MAIN_MENU_BUTTON);

        rows.add(buttons);
        rows.add(mainMenuButton);
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    @Override
    public Map<String, BotState> getNextBotState(Long userId) {
        List<String> workouts = getWorkoutsName(userId);
        Map<String, BotState> nextState = new HashMap<>();
        for (String workout : workouts) {
            nextState.put(workout, BotState.SHOW_TRAINING);
        }
        nextState.put(MAIN_MENU_BUTTON, BotState.SHOW_MAIN_MENU);
        return nextState;
    }

    private List<String> getWorkoutsName(Long userId) {
        List<String> workouts = workoutService.getWorkoutNamesByUserId(userId);
        if (workouts.isEmpty()) {
            log.error("You don`t have workouts");
            throw new NotAddedWorkoutsException("You don`t have workouts");
        }
        return workouts;
    }
}
