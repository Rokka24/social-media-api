package com.khamzin.socialmediaapi.util.exception.user;

import com.khamzin.socialmediaapi.util.exception.ApiException;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException(String message) {
        super(message, "USER_NOT_FOUND");
    }
}
