package com.khamzin.socialmediaapi.util.exception.request;

import com.khamzin.socialmediaapi.util.exception.DefaultErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@ControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<DefaultErrorResponse> handleException(RequestNotFoundException e) {
        DefaultErrorResponse errorResponse = DefaultErrorResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .date(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
