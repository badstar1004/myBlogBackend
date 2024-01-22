package com.example.demo.service;

import com.example.demo.domain.Categories;
import com.example.demo.domain.Post;
import com.example.demo.domain.PostCategories;
import com.example.demo.dto.post.PostTitleViewCountDto;
import com.example.demo.mapper.categories.CategoriesMapper;
import com.example.demo.mapper.post.PostMapper;
import com.example.demo.mapper.postcategories.PostCategoriesMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Autowired
    private PostCategoriesMapper postCategoriesMapper;

    @Autowired
    private PostMapper postMapper;

    public Categories getCategoriesByName(String name) {
        return categoriesMapper.getCategoriesByName(name);
    }

    public PostTitleViewCountDto getPopularPost(Long categoryId) {
        List<PostCategories> postCategoriesList =
            postCategoriesMapper.getPostCategoriesByCategoryId(categoryId);

        return postMapper.getPopularPostInPostIds(postCategoriesList.stream()
                .map(PostCategories::getPostId).collect(Collectors.toList()));
    }
}
