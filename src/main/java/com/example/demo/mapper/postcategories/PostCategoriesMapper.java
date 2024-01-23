package com.example.demo.mapper.postcategories;

import com.example.demo.domain.PostCategories;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PostCategoriesMapper {
	List<PostCategories> getPostCategoriesByCategoryId(Long categoryId);

	int deletePostCategoriesByPost(Long postId);

    List<PostCategories> getPostCategoriesByCategoryName();

	int updatePostCategoriesInPostIds(List<Long> postIds, Long categoryId);

	List<PostCategories> getPostCategoriesInCategoryId(List<Long> postIds);

    int deletePostCategoriesInPost(List<Long> postIds);
}