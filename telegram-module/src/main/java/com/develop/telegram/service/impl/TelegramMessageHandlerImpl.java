package com.develop.telegram.service.impl;

import com.develop.telegram.bot.markup.ReplyKeyboardMarkupItem;
import com.develop.telegram.bot.markup.factory.ReplyKeyboardMarkupFactory;
import com.develop.telegram.exceptions.NotAddedWorkoutsException;
import com.develop.telegram.exceptions.UnsupportedBotStateException;
import com.develop.telegram.service.TelegramMessageHandler;
import com.library.commons.enums.BotState;
import com.library.entity.User;
import com.library.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;

@Log4j
@Service
@RequiredArgsConstructor
public class TelegramMessageHandlerImpl implements TelegramMessageHandler {

    private static final String NOT_ADDED_ERROR_MESSAGE = "Перед початком тренування, його потрібно додати :)";
    private static final String NOT_SUPPORTED_ERROR_MESSAGE = "Перепрошуємо але ця функція ще не реалізована :(";
    private static final String SOMETHING_WENT_WRONG_ERROR_MESSAGE = "Щось пішло не так, будь ласка зверніться до розробника :(";

    private final UserRepository userRepository;
    private final ReplyKeyboardMarkupFactory replyKeyboardMarkupFactory;

    @Override
    public SendMessage processTelegramMessage(Update update) {
        if (update.getMessage() != null && update.getMessage().getChatId() != null) {
            processUser(update);
            return processMessage(update);
        }
        throw new RuntimeException("Message or chatId should be provided");
    }

    private SendMessage processMessage(Update update) {
        Long chatId = update.getMessage().getChatId();
        User user = userRepository.findByChatId(chatId).get();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        setReplyKeyboardMarkup(user, sendMessage, update);
        return sendMessage;
    }

    private void setReplyKeyboardMarkup(User user, SendMessage sendMessage, Update update) {
        try {
            ReplyKeyboardMarkupItem replyKeyboardMarkupItem = replyKeyboardMarkupFactory
                    .getReplyKeyboardMarkupItem(user.getBotState());
            changeUserState(update.getMessage().getText(), replyKeyboardMarkupItem.getNextBotState(user.getId()), user);
            sendMessage.setReplyMarkup(replyKeyboardMarkupItem.getReplyKeyboardMarkup(user.getChatId()));
        } catch (NotAddedWorkoutsException e) {
            setMainPageState(NOT_ADDED_ERROR_MESSAGE, sendMessage, update, user);
            log.error(e);
        } catch (UnsupportedBotStateException e) {
            setMainPageState(NOT_SUPPORTED_ERROR_MESSAGE, sendMessage, update, user);
            log.error(e);
        } catch (Exception e) {
            setMainPageState(SOMETHING_WENT_WRONG_ERROR_MESSAGE, sendMessage, update, user);
            log.error(e);
        }
    }

    private void changeUserState(String message, Map<String, BotState> nextState, User user) {
        BotState state = nextState.get(message);
        if (state != null) {
            user.setBotState(state);
            userRepository.save(user);
        }
    }

    private void processUser(Update update) {
        Long chatId = update.getMessage().getChatId();
        Optional<User> user = userRepository.findByChatId(chatId);
        if (!user.isPresent()) {
            User newUser = User.builder()
                    .chatId(chatId)
                    .botState(BotState.SHOW_MAIN_MENU)
                    .build();
            userRepository.save(newUser);
        }
    }

    private void setMainPageState(String errorMessage, SendMessage sendMessage, Update update, User user) {
        sendMessage.setText(errorMessage);
        sendMessage.setReplyMarkup(replyKeyboardMarkupFactory.getReplyKeyboardMarkupItem(BotState.SHOW_MAIN_MENU)
                .getReplyKeyboardMarkup(update.getMessage().getChatId()));
        setBotState(user, BotState.SHOW_MAIN_MENU);
    }

    private void setBotState(User user, BotState botState) {
        user.setBotState(botState);
        userRepository.save(user);
    }
}
