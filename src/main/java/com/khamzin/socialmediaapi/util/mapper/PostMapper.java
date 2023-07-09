package com.khamzin.socialmediaapi.util.mapper;

import com.khamzin.socialmediaapi.dto.post.PostDto;
import com.khamzin.socialmediaapi.model.post.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final ModelMapper modelMapper;

    public PostDto map(Post post) {
        return PostDto.builder()
                .author(post.getAuthor().getUsername())
                .header(post.getHeader())
                .text(post.getText())
                .images(post.getImages())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public Post map(PostDto dto) {
        return modelMapper.map(dto, Post.class);
    }

    public List<PostDto> map(List<Post> posts) {
        return posts.stream()
                .map(this::map)
                .toList();
    }
}
