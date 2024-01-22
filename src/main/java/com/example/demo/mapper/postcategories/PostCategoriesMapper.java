package com.example.demo.mapper.postcategories;

import com.example.demo.domain.PostCategories;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Post;

@Repository
@Mapper
public interface PostCategoriesMapper {
	List<PostCategories> getPostCategoriesByCategoryId(Long categoryId);

	int deletePostCategoriesByPost(Long postId);
}