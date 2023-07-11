package com.khamzin.socialmediaapi.service;

import com.khamzin.socialmediaapi.dto.user.UserCollectionDto;
import com.khamzin.socialmediaapi.dto.user.UserDto;
import com.khamzin.socialmediaapi.model.user.User;
import com.khamzin.socialmediaapi.repository.UserRepository;
import com.khamzin.socialmediaapi.util.EntityUtil;
import com.khamzin.socialmediaapi.util.exception.user.UserNotFoundException;
import com.khamzin.socialmediaapi.util.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldThrowExceptionIfUserNotFound() {
        Long wrongId = 123L;
        when(userRepository.findById(wrongId)).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(wrongId));
    }


    @Test
    void shouldFindAllUsersWithLimit() {
        int limit = 2;

        List<User> listOfUsers = EntityUtil.getListOfUsers();

        when(userRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(listOfUsers));
        when(userMapper.mapToCollectionDto(any(User.class)))
                .thenAnswer(invocation ->
                {
                    User user = invocation.getArgument(0);
                    return new UserCollectionDto(user.getUsername(), user.getCreatedAt());
                });
        List<UserCollectionDto> result = userService.findAllUsers(limit);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(limit);

    }

    @Test
    void shouldRegisterUserAndReturnDto() {
        UserDto userDto = EntityUtil.getUserDto();
        User user = EntityUtil.getUser();

        when(userMapper.map(any(UserDto.class))).thenReturn(user);
        when(userMapper.map(any(User.class))).thenReturn(userDto);
        when(userRepository.save(user)).thenReturn(user);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn(userDto.getPassword());

        assertThat(userService.registerUser(userDto)).isEqualTo(userDto);
    }
}
