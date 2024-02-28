package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.info("{} {}", HttpStatus.BAD_REQUEST, e.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.info("{} {}", HttpStatus.NOT_FOUND, e.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
    public ErrorResponse handleException(final Exception e) {
        log.info("{} {}", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
    }
}
