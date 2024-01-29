package com.example.demo.domain;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private String status;
    private int viewCount;
    private String categoryName;
    private String imageUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    // 필요한 경우, 추가적인 메소드나 로직
}
