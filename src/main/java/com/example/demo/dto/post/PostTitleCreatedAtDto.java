package com.example.demo.dto.post;

import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostTitleCreatedAtDto {

    private String title;
    private Timestamp createdAt;
}
