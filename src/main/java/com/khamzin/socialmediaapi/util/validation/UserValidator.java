package com.khamzin.socialmediaapi.util.validation;

import com.khamzin.socialmediaapi.dto.user.UserDto;
import com.khamzin.socialmediaapi.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target,
                         @NotNull Errors errors) {
        UserDto userDto = (UserDto) target;

        if (userService.getUserDtoByUsername(userDto.getUsername()).isPresent()) {
            errors.reject("username", "User with such name is already exist");
        }
    }
}
