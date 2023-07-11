package com.khamzin.socialmediaapi.util;

import com.khamzin.socialmediaapi.dto.post.PostDto;
import com.khamzin.socialmediaapi.dto.request.RequestDto;
import com.khamzin.socialmediaapi.dto.user.UserDto;
import com.khamzin.socialmediaapi.model.post.Post;
import com.khamzin.socialmediaapi.model.request.Request;
import com.khamzin.socialmediaapi.model.request.Status;
import com.khamzin.socialmediaapi.model.request.Type;
import com.khamzin.socialmediaapi.model.user.User;
import com.khamzin.socialmediaapi.model.user.UserRole;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class EntityUtil {

    public static List<User> getListOfUsers() {
        User user1 = User.builder()
                .id(1L)
                .username("User1")
                .password("12345")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .enabled(true)
                .role(UserRole.USER)
                .friends(Collections.emptyList())
                .requests(Collections.emptyList())
                .subscribers(Collections.emptyList())
                .subscriptions(Collections.emptyList())
                .build();
        User user2 = User.builder()
                .id(2L)
                .username("User2")
                .password("12345")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .enabled(true)
                .role(UserRole.USER)
                .friends(Collections.emptyList())
                .requests(Collections.emptyList())
                .subscribers(Collections.emptyList())
                .subscriptions(Collections.emptyList())
                .build();

        return List.of(user1, user2);
    }

    public static User getUser() {
        return User.builder()
                .id(1L)
                .username("User123")
                .password("12345")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .enabled(true)
                .role(UserRole.USER)
                .posts(new ArrayList<>())
                .friends(new ArrayList<>())
                .requests(new ArrayList<>())
                .subscribers(new ArrayList<>())
                .subscriptions(new ArrayList<>())
                .build();
    }

    public static User getAnotherUser() {
        return User.builder()
                .id(2L)
                .username("User222")
                .password("12345")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .enabled(true)
                .role(UserRole.USER)
                .posts(new ArrayList<>())
                .friends(new ArrayList<>())
                .requests(new ArrayList<>())
                .subscribers(new ArrayList<>())
                .subscriptions(new ArrayList<>())
                .build();
    }

    public static UserDto getUserDto() {
        return UserDto.builder()
                .username("user123")
                .password("12345")
                .role(UserRole.USER)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static PostDto getPostDto() {
        return PostDto.builder()
                .author("user")
                .header("Header")
                .text("Text")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Post getPost() {
        return Post.builder()
                .id(1L)
                .author(getUser())
                .header("Header")
                .text("Text")
                .build();
    }

    public static List<Post> getPosts(int copies) {
        return Collections.nCopies(copies, getPost());
    }

    public static List<PostDto> getPostsDto(int copies) {
        return Collections.nCopies(copies, getPostDto());
    }

    public static Request getRequest() {
        return Request.builder()
                .id(1L)
                .type(Type.FRIEND)
                .status(Status.WAITING_FOR_ANSWER)
                .sender(getUser())
                .recipient(getAnotherUser())
                .build();
    }

    public static RequestDto getRequestDto() {
        return RequestDto.builder()
//                .senderId(1L)
//                .recipientId(2L)
                .build();
    }
}
