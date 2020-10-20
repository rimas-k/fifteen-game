package com.someorg.fifteengame.repositories.exceptions;

public class GameAlreadyExistsException extends Exception {

    public GameAlreadyExistsException(String message) {
        super(message);
    }

}
