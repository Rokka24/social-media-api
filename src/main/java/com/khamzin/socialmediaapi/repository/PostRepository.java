package com.khamzin.socialmediaapi.repository;

import com.khamzin.socialmediaapi.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = """
            select p.id, p.header, p.text, p.created_at, p.updated_at, p.images, p.author_id
            from user_subscriptions us
                join post p on p.author_id = us.subscription_id
            where us.user_id = :userId
            order by p.created_at desc
            offset :offset rows fetch first :limit rows only""",
            nativeQuery = true)
    List<Post> findPostsForFeedByUserIdWithPaginationAndSortingByCreation(@Param("userId") Long userId,
                                                                          @Param("offset") int offset,
                                                                          @Param("limit") int limit);
}
