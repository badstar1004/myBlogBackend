package com.example.demo.mapper.categories;

import com.example.demo.domain.Categories;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CategoriesMapper {
    Categories getCategoriesByName(String name);

    int deleteCategory(Long categoryId);
}
