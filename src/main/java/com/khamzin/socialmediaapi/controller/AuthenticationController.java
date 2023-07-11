package com.khamzin.socialmediaapi.controller;

import com.khamzin.socialmediaapi.dto.auth.AuthRequestDto;
import com.khamzin.socialmediaapi.dto.auth.AuthResponseDto;
import com.khamzin.socialmediaapi.dto.user.UserDto;
import com.khamzin.socialmediaapi.security.CustomUser;
import com.khamzin.socialmediaapi.security.SecurityService;
import com.khamzin.socialmediaapi.security.TokenDetails;
import com.khamzin.socialmediaapi.service.UserService;
import com.khamzin.socialmediaapi.util.validation.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(description = "Register endpoint for users",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Validation error",
                            responseCode = "400"
                    )
            })
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

    @Operation(description = "Login endpoint for users",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Validation error",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "401"
                    )
            })
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

    @Operation(description = "Endpoint to get info about authenticated user",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "401"
                    )
            })
    @GetMapping("/information")
    public UserDto getUserInfo(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        log.info("Get user - {} info in controller", customUser);
        return userService.getUserInfo(customUser.getUserId());
    }
}
