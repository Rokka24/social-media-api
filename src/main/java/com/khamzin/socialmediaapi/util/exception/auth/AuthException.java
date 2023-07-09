package com.khamzin.socialmediaapi.util.exception.auth;

import com.khamzin.socialmediaapi.util.exception.ApiException;

public class AuthException extends ApiException {
    public AuthException(String message, String errorCode) {
        super(message, errorCode);
    }
}
