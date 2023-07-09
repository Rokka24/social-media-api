package com.khamzin.socialmediaapi.util.exception.post;

import com.khamzin.socialmediaapi.util.exception.ApiException;

public class PostNotFoundException extends ApiException {
    public PostNotFoundException(String message) {
        super(message, "USER_NOT_FOUND");
    }
}
