package com.showcase.application.springbootbackend.controllers;

import com.showcase.application.springbootbackend.dto.response.BadResponse;
import com.showcase.application.springbootbackend.exceptions.ResourceExistsException;
import com.showcase.application.springbootbackend.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class RestApiAdvise {

    @ExceptionHandler(
            exception = ResourceNotFoundException.class,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    protected BadResponse badRequest(ResourceNotFoundException ex, WebRequest request) {
        return BadResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(
            exception = ResourceExistsException.class,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.CONFLICT)
    protected BadResponse badRequest(ResourceExistsException ex, WebRequest request) {
        return BadResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .path(request.getDescription(false))
                .build();
    }
}
