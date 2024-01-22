package com.example.demo.dto.user;

import com.example.demo.dto.post.PostTitleCreatedAtDto;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@Builder
public class UserPostListDto {
    private Long userId;
    private List<PostTitleCreatedAtDto> userPostList;

}
