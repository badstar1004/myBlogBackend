package com.example.demo.dto.post;

import com.example.demo.domain.Post;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PopularPostsDto {

    List<Post> mostCommentsPosts;
    List<Post> mostViewCountPosts;
}
