package com.khamzin.socialmediaapi.controller;

import com.khamzin.socialmediaapi.dto.auth.AuthRequestDto;
import com.khamzin.socialmediaapi.dto.auth.AuthResponseDto;
import com.khamzin.socialmediaapi.dto.user.UserDto;
import com.khamzin.socialmediaapi.security.CustomUser;
import com.khamzin.socialmediaapi.security.SecurityService;
import com.khamzin.socialmediaapi.security.TokenDetails;
import com.khamzin.socialmediaapi.service.UserService;
import com.khamzin.socialmediaapi.util.validation.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.khamzin.socialmediaapi.util.exception.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final SecurityService securityService;
    private final UserService userService;
    private final UserValidator userValidator;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto userDto,
                                      BindingResult bindingResult) {
        userValidator.validate(userDto, bindingResult);

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        UserDto userToRegister = userService.registerUser(userDto);
        log.info("Registered user - {}", userToRegister);
        return new ResponseEntity<>(userToRegister, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequestDto authRequestDto,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        TokenDetails tokenDetails = securityService.authenticate(authRequestDto.getUsername(), authRequestDto.getPassword());
        log.info("Login user - {} info in controller", authRequestDto.getUsername());
        AuthResponseDto authResponseDto = AuthResponseDto.builder()
                .userId(tokenDetails.getUserId())
                .token(tokenDetails.getToken())
                .issuedAt(tokenDetails.getIssuedAt())
                .expiresAt(tokenDetails.getExpiresAt())
                .build();
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @GetMapping("/information")
    public UserDto getUserInfo(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        log.info("Get user - {} info in controller", customUser);
        return userService.getUserInfo(customUser.getUserId());
    }
}
