package com.khamzin.socialmediaapi.service;

import com.khamzin.socialmediaapi.dto.user.UserCollectionDto;
import com.khamzin.socialmediaapi.dto.user.UserDto;
import com.khamzin.socialmediaapi.model.user.User;
import com.khamzin.socialmediaapi.model.user.UserRole;
import com.khamzin.socialmediaapi.repository.UserRepository;
import com.khamzin.socialmediaapi.util.exception.auth.AuthException;
import com.khamzin.socialmediaapi.util.exception.user.UserNotFoundException;
import com.khamzin.socialmediaapi.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserCollectionDto> findAllUsers(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        return userRepository.findAll(pageRequest)
                .stream()
                .map(userMapper::mapToCollectionDto)
                .toList();
    }

    @Transactional
    public UserDto registerUser(UserDto userDto) {
        UserDto userToRegister = userDto.toBuilder()
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(UserRole.USER)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(userMapper.map(userToRegister));

        return userMapper.map(savedUser);
    }

    @Transactional(readOnly = true)
    public UserDto getUserInfo(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::map)
                .orElseThrow(() -> new AuthException("Can't show a user info", "USER_INFO_ERROR"));
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public UserDto getUserDtoById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.map(user);
    }

    @Transactional
    public Optional<User> getUserDtoByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}