package com.someorg.fifteengame.repositories.exceptions;

public class GameAlreadyExistsException extends RuntimeException {

    public GameAlreadyExistsException(String message) {
        super(message);
    }

}
