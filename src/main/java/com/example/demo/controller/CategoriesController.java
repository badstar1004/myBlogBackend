package com.example.demo.controller;

import com.example.demo.dto.post.PostTitleViewCountDto;
import com.example.demo.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    // 9. 특정 카테고리의 게시물 중 가장 많이 조회된 게시물
    @GetMapping("/{categoryId}/popular-post")
    public ResponseEntity<PostTitleViewCountDto> getPopularPost(@PathVariable Long categoryId) {
        PostTitleViewCountDto titleViewCountDto = categoriesService.getPopularPost(categoryId);
        if(titleViewCountDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(titleViewCountDto, HttpStatus.OK);
    }
}
