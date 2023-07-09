package com.khamzin.socialmediaapi.util.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
@Builder
public class DefaultErrorResponse {
    private HttpStatus httpStatus;
    private String message;
    private String date;
}
