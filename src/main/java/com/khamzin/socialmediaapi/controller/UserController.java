package com.khamzin.socialmediaapi.controller;

import com.khamzin.socialmediaapi.dto.user.UserCollectionDto;
import com.khamzin.socialmediaapi.dto.user.UserDto;
import com.khamzin.socialmediaapi.service.UserService;
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

    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserDto userDto = userService.getUserDtoById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "100", name = "limit") int limit) {
        List<UserCollectionDto> allUsers = userService.findAllUsers(limit);

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
}
