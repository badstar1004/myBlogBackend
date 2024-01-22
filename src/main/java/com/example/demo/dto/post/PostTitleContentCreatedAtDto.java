package com.example.demo.dto.post;

import java.sql.Timestamp;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostTitleContentCreatedAtDto {

    private String title;
    private String content;
    private Timestamp createdAt;
}
