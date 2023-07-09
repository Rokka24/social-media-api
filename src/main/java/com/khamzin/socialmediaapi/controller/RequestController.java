package com.khamzin.socialmediaapi.controller;

import com.khamzin.socialmediaapi.dto.request.RequestDto;
import com.khamzin.socialmediaapi.model.request.Status;
import com.khamzin.socialmediaapi.service.RequestService;
import com.khamzin.socialmediaapi.util.validation.RequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.khamzin.socialmediaapi.util.exception.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final RequestValidator requestValidator;

    @PostMapping("/new")
    public ResponseEntity<?> sendRequest(@RequestBody @Valid RequestDto requestDto,
                                         BindingResult bindingResult) {
        requestValidator.validate(requestDto, bindingResult);

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        RequestDto updatedRequestDto = requestService.sendFriendRequest(requestDto);

        return new ResponseEntity<>(updatedRequestDto, HttpStatus.CREATED);
    }

    @PostMapping("{id}")
    public ResponseEntity<?> answer(@PathVariable("id") Long requestId,
                                    @RequestParam Boolean answer) {
        Status status = requestService.answerToRequest(requestId, answer);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> cancel(@PathVariable("id") Long requestId) {
        requestService.cancelFriendRequest(requestId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getRequest(@PathVariable("id") Long requestId) {
        RequestDto requestDto = requestService.getRequestDtoById(requestId);

        return new ResponseEntity<>(requestDto, HttpStatus.OK);
    }
}
