package com.example.demo.dto.post;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostByCategoriesDto {
    private String category_name;
    private List<PostDto> postList;

}
