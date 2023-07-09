package com.khamzin.socialmediaapi.controller;

import com.khamzin.socialmediaapi.dto.post.PostDto;
import com.khamzin.socialmediaapi.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.khamzin.socialmediaapi.util.exception.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping()
    public ResponseEntity<?> getAllUserPosts(@RequestParam("userId") Long userId) {
        List<PostDto> posts = postService.getAllPostFromUserById(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createPost(@RequestParam("userId") Long id,
                                        @RequestBody @Valid PostDto postDto,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        PostDto newPost = postService.createNewPost(id, postDto);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PostMapping("/{postId}/edit")
    public ResponseEntity<?> updatePost(@PathVariable Long postId,
                                        @RequestBody @Valid PostDto postDto,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        PostDto updatedPost = postService.updatePost(postId, postDto);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }
}
