package com.khamzin.socialmediaapi.controller;

import com.khamzin.socialmediaapi.dto.post.PostDto;
import com.khamzin.socialmediaapi.security.CustomUser;
import com.khamzin.socialmediaapi.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @Operation(description = "Endpoint to get user's subscriptions post with pagination and sorted by time of creation",
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
    public ResponseEntity<?> showFeed(Authentication authentication,
                                      @RequestParam(defaultValue = "1", name = "page_number") int pageNumber,
                                      @RequestParam(defaultValue = "10", name = "page_size") int pageSize) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Long id = customUser.getUserId();
        List<PostDto> postDtos = feedService.showFeed(id, pageNumber, pageSize);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }
}
