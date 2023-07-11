package com.khamzin.socialmediaapi.controller;

import com.khamzin.socialmediaapi.dto.user.UserCollectionDto;
import com.khamzin.socialmediaapi.dto.user.UserDto;
import com.khamzin.socialmediaapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(description = "Endpoint to get a single user by user id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "User not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "401"
                    )
            })
    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserDto userDto = userService.getUserDtoById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Operation(description = "Endpoint for getting all users with limit(default = 100)",
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
    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "100", name = "limit") int limit) {
        List<UserCollectionDto> allUsers = userService.findAllUsers(limit);

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
}
