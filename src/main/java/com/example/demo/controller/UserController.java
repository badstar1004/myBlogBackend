package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.post.PostTitleContentCreatedAtDto;
import com.example.demo.dto.user.UserPostCount;
import com.example.demo.dto.user.UserPostListDto;
import com.example.demo.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 2. 특정 사용자의 게시물 조회
    @GetMapping("/{userId}/posts")
    public ResponseEntity<UserPostListDto> getUserPostList(@PathVariable Long userId) {
        UserPostListDto userPostList = userService.getUserPostList(userId);

        if(userPostList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userPostList, HttpStatus.OK);
    }

    // 5. 각 사용자별 게시물 수 조회
    @GetMapping("/posts-count")
    public ResponseEntity<List<UserPostCount>> getUserPostsCount() {
        List<UserPostCount> userPostCountList = userService.getUserPostCount();
        if(userPostCountList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userPostCountList, HttpStatus.OK);
    }

    // 7. 특정 사용자의 최근 게시물 조회
    @GetMapping("/{userId}/recent")
    public ResponseEntity<List<PostTitleContentCreatedAtDto>> getUserPostRecent(@PathVariable Long userId) {
        List<PostTitleContentCreatedAtDto> userPostRecentList = userService.getUserPostRecent(userId);
        if(userPostRecentList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userPostRecentList, HttpStatus.OK);
    }

    // 12. 다수의 사용자 조회
    @GetMapping("/users-paging")
    public ResponseEntity<List<User>> getUsersPaging(
        @RequestParam(required = false) List<String> sort,
        @RequestParam(required = false) List<String> order,
        @RequestParam(required = false) Long page,
        @RequestParam(required = false) Long offset
    ) {
        Map<String, Object> pagingMap = new HashMap<>();
        pagingMap.put("sort", sort);
        pagingMap.put("order", order);
        pagingMap.put("page", page);
        pagingMap.put("offset", offset);

        List<User> usersPaging = userService.getUsersPaging(pagingMap);
        if(usersPaging == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(usersPaging, HttpStatus.OK);
    }

    // 삭제 - 댓글을 삭제하는 경우 댓글에 대댓글이 있는 경우도 포함할 수 있게 삭제 로직을 구성
    // 1. 특정 사용자 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        if(userService.deleteUser(userId) <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }
}
