package com.example.demo.dto.post;

import java.sql.Timestamp;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PostDto {

    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private String status;
    private int viewCount;
    private Timestamp created_at;
}