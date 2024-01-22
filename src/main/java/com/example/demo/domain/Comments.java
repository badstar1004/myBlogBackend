package com.example.demo.domain;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class Comments {

    private Long commentId;
    private Long postId;
    private Long userId;
    private Long parentCommentId;
    private String comments;
    private Timestamp createdAt;
}
