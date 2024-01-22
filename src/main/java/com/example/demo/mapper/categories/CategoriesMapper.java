package com.example.demo.mapper.categories;

import com.example.demo.domain.Categories;
import com.example.demo.dto.post.PostTitleViewCountDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CategoriesMapper {
    Categories getCategoriesByName(String name);

    PostTitleViewCountDto getCategoriesById(Long categoryId);

}
