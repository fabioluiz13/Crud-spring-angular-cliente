package com.fabio.controller;

import com.fabio.exception.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handerValidationErros(MethodArgumentNotValidException ex){
        BindingResult result = ex.getBindingResult();
        List<String> messages = result.getAllErrors()
                .stream()
                .map(ObjectError -> ObjectError.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(messages);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleResponseStatusException (ResponseStatusException ex){
        String messageError = ex.getMessage();
        HttpStatus status = ex.getStatus();
        ApiErrors errors = new ApiErrors(messageError);
        return new ResponseEntity<>(errors, status);
    }
}
