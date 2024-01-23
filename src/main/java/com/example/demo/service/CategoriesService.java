package com.example.demo.service;

import com.example.demo.domain.Categories;
import com.example.demo.domain.PostCategories;
import com.example.demo.dto.post.PostTitleViewCountDto;
import com.example.demo.mapper.categories.CategoriesMapper;
import com.example.demo.mapper.comment.CommentMapper;
import com.example.demo.mapper.post.PostMapper;
import com.example.demo.mapper.postcategories.PostCategoriesMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Autowired
    private PostCategoriesMapper postCategoriesMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    public Categories getCategoriesByName(String name) {
        return categoriesMapper.getCategoriesByName(name);
    }

    public PostTitleViewCountDto getPopularPost(Long categoryId) {
        List<PostCategories> postCategoriesList =
            postCategoriesMapper.getPostCategoriesByCategoryId(categoryId);

        return postMapper.getPopularPostInPostIds(postCategoriesList.stream()
                .map(PostCategories::getPostId).toList());
    }

    public int deleteCategory(Long categoryId) {
        int totalDeleted = 0;

        // PostCategory 조회
        List<PostCategories> postCategoriesList =
            postCategoriesMapper.getPostCategoriesByCategoryId(categoryId);

        // PostCategories 하위 삭제
        if(!postCategoriesList.isEmpty()) {
            // PostCategories 삭제
            List<Long> postIds = postCategoriesList.stream()
                .map(PostCategories::getPostId).toList();

            totalDeleted += postCategoriesMapper.deletePostCategoriesInPost(postIds);

            // Post 기준 comments 삭제
            totalDeleted += commentMapper.deleteCommentsInPostId(postIds);

            // Post 삭제
            totalDeleted += postMapper.deletePostInPostId(postIds);
        }
        
        // categories 삭제
        totalDeleted += categoriesMapper.deleteCategory(categoryId);

        return totalDeleted;
    }

    @Transactional
    public int deletePosts(Long categoryId) {
        int totalDeleted = 0;

        // PostCategory 조회
        List<PostCategories> postCategoriesList =
            postCategoriesMapper.getPostCategoriesByCategoryId(categoryId);

        List<Long> postIds = postCategoriesList.stream()
            .map(PostCategories::getPostId).toList();

        // Post 기준 comments 삭제
        totalDeleted += commentMapper.deleteCommentsInPostId(postIds);

        // PostCategory 삭제
        totalDeleted += postCategoriesMapper.deletePostCategoriesInPost(postIds);

        // Post 삭제
        totalDeleted += postMapper.deletePostInPostId(postIds);

        return totalDeleted;
    }
}
