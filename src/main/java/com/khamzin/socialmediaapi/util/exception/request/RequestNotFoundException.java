package com.khamzin.socialmediaapi.util.exception.request;

import com.khamzin.socialmediaapi.util.exception.ApiException;

public class RequestNotFoundException extends ApiException {
    public RequestNotFoundException(String message) {
        super(message, "Request not found");
    }
}
