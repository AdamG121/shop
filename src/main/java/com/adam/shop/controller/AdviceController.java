package com.adam.shop.controller;

import com.adam.shop.domain.dto.ErrorFieldDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;


// klasa z metodami do obsługi errorów http
@Slf4j //generyczny logger - dostosuje się do implementacji loggera, który mamy dodany do aplikacji
@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
        //dzięki tej adnotacji będą przechwytywane błędy tego typu
    void handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("Entity doesn't exist", e); //Uwaga nie można stosować sout
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    void handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.error("Entity already exist", e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<ErrorFieldDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Bad request", e);
        //kod dzięki któremu dostaniemy się do wszystkich errorów
//        FieldError objectError = (FieldError) allErrors.get(0);
//        objectError.getDefaultMessage();
//        objectError.getField();
        return e.getBindingResult().getAllErrors().stream()
                .map(objectError -> {
                    if(objectError instanceof FieldError) {
                        FieldError error = (FieldError) objectError;
                        return new ErrorFieldDto(error.getField(), error.getDefaultMessage());
                    }
                    return new ErrorFieldDto(objectError.getDefaultMessage(), null);
                })
                .collect(Collectors.toList());
    }

    //jak będę chciał zapisać użytkownika z id
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    List<ErrorFieldDto> handleConstraintViolationException (ConstraintViolationException e){
        log.error("Bad request", e);
        return e.getConstraintViolations().stream()
                .map(error -> new ErrorFieldDto(error.getMessage(), error.getPropertyPath().toString()))
                .collect(Collectors.toList());
    }

}
