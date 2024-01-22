package com.example.demo.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListDto {

    private String title;
    private String content;
    private int viewCount;
}
