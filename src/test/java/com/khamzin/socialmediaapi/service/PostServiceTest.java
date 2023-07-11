package com.khamzin.socialmediaapi.service;

import com.khamzin.socialmediaapi.dto.post.PostDto;
import com.khamzin.socialmediaapi.model.post.Post;
import com.khamzin.socialmediaapi.model.user.User;
import com.khamzin.socialmediaapi.repository.PostRepository;
import com.khamzin.socialmediaapi.util.EntityUtil;
import com.khamzin.socialmediaapi.util.exception.user.UserNotFoundException;
import com.khamzin.socialmediaapi.util.mapper.PostMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private PostMapper postMapper;
    @Mock
    private UserService userService;

    @Test
    void shouldThrowExceptionIfUserNotFound() {
        Long wrongId = 123L;
        when(postRepository.findById(wrongId)).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> postService.getPostById(wrongId));
    }

    @Test
    void shouldCreateAndReturnNewPostDto() {
        Long userId = 1L;

        PostDto postDto = EntityUtil.getPostDto();
        Post post = EntityUtil.getPost();
        User user = EntityUtil.getUser();

        post.setAuthor(user);

        when(userService.getUserById(userId)).thenReturn(user);
        when(postMapper.map(postDto)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);

        postDto.setAuthor(user.getUsername());
        postDto.setCreatedAt(LocalDateTime.now());

        assertThat(postService.createNewPost(userId, postDto)).isEqualTo(postDto);
        assertThat(user.getPosts().size()).isEqualTo(1);
    }

    @Test
    void shouldReturnSortedAndPaginatedPostFromUserSubscriptions() {
        Long userId = 1L;
        int pageNumber = 1;
        int pageSize = 10;

        int copies = 10;
        List<Post> posts = EntityUtil.getPosts(copies);
        List<PostDto> postsDto = EntityUtil.getPostsDto(copies);

        when(postRepository.findPostsForFeedByUserIdWithPaginationAndSortingByCreation(userId, 0, pageSize))
                .thenReturn(posts);
        when(postMapper.map(posts)).thenReturn(postsDto);
        assertThat(postService.getSortedPostsFromSubscriptions(userId, pageNumber, pageSize).size())
                .isEqualTo(postsDto.size());
    }
}
