package com.khamzin.socialmediaapi.util.exception.validation;

import com.khamzin.socialmediaapi.util.exception.ApiException;

public class ValidationException extends ApiException {
    public ValidationException(String message, String errorCode) {
        super(message, errorCode);
    }
}
