package com.someorg.fifteengame.validation.advice;


import com.someorg.fifteengame.exceptions.GameAlreadyExistsException;
import com.someorg.fifteengame.exceptions.GameNotFoundException;
import com.someorg.fifteengame.exceptions.GameTileNotFoundException;
import com.someorg.fifteengame.validation.dto.GameExceptionResponse;
import com.someorg.fifteengame.validation.dto.ValidationErrorResponse;
import com.someorg.fifteengame.validation.dto.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ResponseBody
    @ExceptionHandler(GameTileNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GameExceptionResponse onGameTileNotFoundException(GameTileNotFoundException exception) {
        GameExceptionResponse response = new GameExceptionResponse();
        response.setMessage("Invalid game tile to move.");

        return response;
    }

    @ResponseBody
    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GameExceptionResponse onGameNotFoundException(GameNotFoundException exception) {
        GameExceptionResponse response = new GameExceptionResponse();
        response.setMessage("The game specified by identifiers does not exist.");

        return response;
    }

    @ResponseBody
    @ExceptionHandler(GameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public GameExceptionResponse onGameAlreadyExistsException(GameAlreadyExistsException exception) {
        GameExceptionResponse response = new GameExceptionResponse();
        response.setMessage("The game specified by identifiers already exists.");

        return response;
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintViolationException(ConstraintViolationException exception) {
        ValidationErrorResponse response = new ValidationErrorResponse();

        for (ConstraintViolation constraintViolation : exception.getConstraintViolations()) {
            Violation violation = createViolation(constraintViolation);
            response.addToViolations(violation);
        }

        return response;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ValidationErrorResponse response = new ValidationErrorResponse();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            Violation violation = createViolation(fieldError);
            response.addToViolations(violation);
        }

        return response;
    }

    private Violation createViolation(ConstraintViolation constraintViolation) {
        String propertyPath = constraintViolation.getPropertyPath().toString();
        String message = constraintViolation.getMessage();

        return new Violation(propertyPath, message);
    }

    private Violation createViolation(FieldError fieldError) {
        String propertyPath = fieldError.getField();
        String message = MessageFormat.format("{0}, actual value \"{1}\"", fieldError.getDefaultMessage(), fieldError.getRejectedValue().toString());

        return new Violation(propertyPath, message);
    }

}
