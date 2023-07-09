package com.khamzin.socialmediaapi.service;

import com.khamzin.socialmediaapi.dto.post.PostDto;
import com.khamzin.socialmediaapi.model.post.Post;
import com.khamzin.socialmediaapi.model.user.User;
import com.khamzin.socialmediaapi.repository.PostRepository;
import com.khamzin.socialmediaapi.util.exception.post.PostNotFoundException;
import com.khamzin.socialmediaapi.util.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional
    @PreAuthorize("#userId == authentication.principal.userId")
    public PostDto createNewPost(Long userId, PostDto postDto) {
        User user = userService.getUserById(userId);
        postDto.setAuthor(user.getUsername());
        postDto.setCreatedAt(LocalDateTime.now());
        Post post = postMapper.map(postDto);
        post.setAuthor(user);

        postRepository.save(post);
        user.getPosts().add(post);

        return postDto;
    }

    @Transactional(readOnly = true)
    public List<PostDto> getAllPostFromUserById(Long id) {
        return userService.getUserById(id).getPosts()
                .stream()
                .map(postMapper::map)
                .toList();
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    @Transactional
    @PostAuthorize("returnObject.author == authentication.principal.username")
    public PostDto updatePost(Long postId, PostDto updatedPost) {
        Post postById = getPostById(postId);
        Post builtPost = postById.toBuilder()
                .header(updatedPost.getHeader())
                .text(updatedPost.getText())
                .images(updatedPost.getImages())
                .updatedAt(LocalDateTime.now())
                .author(postById.getAuthor())
                .build();
        postRepository.save(builtPost);
        return postMapper.map(builtPost);
    }

    @Transactional(readOnly = true)
    public List<PostDto> getSortedPostsFromSubscriptions(Long userId, int pageNumber, int pageSize) {
        pageNumber = (pageNumber - 1) * pageSize;
        List<Post> posts = postRepository.findPostsForFeedByUserIdWithPaginationAndSortingByCreation(userId, pageNumber, pageSize);
        return postMapper.map(posts);
    }
}
