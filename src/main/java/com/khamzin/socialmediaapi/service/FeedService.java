package com.khamzin.socialmediaapi.service;

import com.khamzin.socialmediaapi.dto.post.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final PostService postService;

    @Transactional(readOnly = true)
    public List<PostDto> showFeed(Long userId, int pageNumber, int pageSize) {
        return postService.getSortedPostsFromSubscriptions(userId, pageNumber, pageSize);
    }
}
