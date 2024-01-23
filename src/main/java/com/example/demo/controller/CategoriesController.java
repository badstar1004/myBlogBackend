package com.example.demo.controller;

import com.example.demo.dto.post.PostTitleViewCountDto;
import com.example.demo.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    // 삭제
    // 2. 특정 카테고리 삭제
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        if(categoriesService.deleteCategory(categoryId) <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }

    // 삭제
    // 3. 특정 카테고리에 속한 게시물 삭제
    @DeleteMapping("/{categoryId}/posts")
    public ResponseEntity<String> deletePosts(@PathVariable Long categoryId) {
        if(categoriesService.deletePosts(categoryId) <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }
}
