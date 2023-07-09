package com.khamzin.socialmediaapi.util.validation;

import com.khamzin.socialmediaapi.dto.request.RequestDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import org.springframework.validation.Validator;

@Component
public class RequestValidator implements Validator {


    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return RequestDto.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target,
                         @NotNull Errors errors) {
        RequestDto requestDto = (RequestDto) target;

        if (requestDto.getRecipientId().equals(requestDto.getSenderId())) {
            errors.rejectValue("recipient_id", "fieldsNotEqual",
                    "Fields should be different");
        }

    }
}
