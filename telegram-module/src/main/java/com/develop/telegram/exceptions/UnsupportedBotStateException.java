package com.develop.telegram.exceptions;

public class UnsupportedBotStateException extends RuntimeException{

    public UnsupportedBotStateException(String message) {
        super(message);
    }
}
