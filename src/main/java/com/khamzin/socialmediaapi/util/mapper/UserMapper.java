package com.khamzin.socialmediaapi.util.mapper;

import com.khamzin.socialmediaapi.dto.user.UserCollectionDto;
import com.khamzin.socialmediaapi.dto.user.UserDto;
import com.khamzin.socialmediaapi.model.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserDto map(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User map(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    public UserCollectionDto mapToCollectionDto(User user) {
        return modelMapper.map(user, UserCollectionDto.class);
    }
}
