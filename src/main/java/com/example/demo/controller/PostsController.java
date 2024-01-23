package com.example.demo.controller;

import com.example.demo.domain.Comments;
import com.example.demo.domain.Post;
import com.example.demo.domain.PostCategories;
import com.example.demo.dto.post.PostByCategoriesDto;
import com.example.demo.dto.post.PostCommentCountDto;
import com.example.demo.dto.post.form.PostIdListForm;
import com.example.demo.dto.post.PostListDto;
import com.example.demo.dto.post.PostTitleCreatedAtDto;
import com.example.demo.dto.post.PostTitleViewCountDto;
import com.example.demo.service.PostService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController //컨트롤러가 HTTP 요청을 처리하고, 자바 객체를 HTTP 응답 본문으로 직접 반환하도록 합니다.
/*
 * 1. 기본적인 컨트롤러의 기능 - HTTP 요청을 받아들이는 엔드포인트(endpoints)로 사용
 * 2. 자동 역직렬화(Request Body -> Java Object): 클라이언트로부터 오는 JSON 형식의 HTTP 요청 본문을 자바 객체로 자동으로 변환합니다. 
 * 3. 자동 직렬화(Java Object -> Response Body): 자바 객체를 JSON 형식의 HTTP 응답 본문으로 자동 변환하여 클라이언트에게 보냅니다. 
 * */
@RequestMapping("/posts") // 컨트롤러의 URI 구성
public class PostsController {

	@Autowired 	//의존성 주입
    private PostService postsService;

    // 게시물 생성 (POST)
    @PostMapping // http method 설정
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        	postsService.createPost(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 모든 게시물 조회
    @GetMapping
    public ResponseEntity<List<Post>> getPostList() {
        List<Post> allPosts = postsService.getAllPosts();
        if(allPosts == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    // 1. public 게시물 조회
    @GetMapping("/public-posts")
    public ResponseEntity<List<PostListDto>> getPublicPostList() {
        List<PostListDto> allPosts = postsService.getPublicPostList();
        if(allPosts == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    // 3. 상위 5개 게시물 조회
    @GetMapping("/top-posts")
    public ResponseEntity<List<PostTitleViewCountDto>> getPostTop() {
        List<PostTitleViewCountDto> allPosts = postsService.getPostTop();
        if(allPosts == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    // 4. 특정 카테고리에 속하는 게시물 조회
    @GetMapping("/{categoryName}/posts-category")
    public ResponseEntity<PostByCategoriesDto> getPostTop(@PathVariable String categoryName) {
        PostByCategoriesDto postByCategoriesDto = postsService.getPostCategoriesByCategoriesId(categoryName);
        if(postByCategoriesDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(postByCategoriesDto, HttpStatus.OK);
    }

    // 6. 특정 게시물에 대한 모든 댓글 조회
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comments>> getPostComments(@PathVariable Long postId) {
        List<Comments> commentsList = postsService.getPostAllComments(postId);
        if(commentsList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(commentsList, HttpStatus.OK);
    }

    // 8. 각 게시물별 댓글 수 조회
    @GetMapping("/comment-count")
    public ResponseEntity<List<PostCommentCountDto>> getPostComments() {
        List<PostCommentCountDto> postAllCommentCountList = postsService.getPostComments();
        if(postAllCommentCountList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(postAllCommentCountList, HttpStatus.OK);
    }

    // 10. 특정 기간 동안 작성된 게시물 조회
    @GetMapping("/specific-period")
    public ResponseEntity<List<PostTitleCreatedAtDto>> getPostPeriod() {
        List<PostTitleCreatedAtDto> postTitleCreatedAtDtoList = postsService.getPostPeriod();
        if(postTitleCreatedAtDtoList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(postTitleCreatedAtDtoList, HttpStatus.OK);
    }

    // 11. 다수의 게시물 조회
    @GetMapping("/posts-paging")
    public ResponseEntity<List<Post>> getPostsPaging(
        @RequestParam(required = false) List<String> sort,
        @RequestParam(required = false) List<String> order,
        @RequestParam(required = false) Long page,
        @RequestParam(required = false) Long offset) {

        Map<String, Object> pagingMap = new HashMap<>();
        pagingMap.put("sort", sort);
        pagingMap.put("order", order);
        pagingMap.put("page", page);
        pagingMap.put("offset", offset);

        List<Post> postsPaging = postsService.getPostsPaging(pagingMap);
        if(postsPaging == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(postsPaging, HttpStatus.OK);
    }

    // 수정
    // 1. 특정 게시물의 조회수 증가
    @PatchMapping("/{postId}")
    public ResponseEntity<Post> updatePostViewCount(@PathVariable Long postId) {
        Post post = postsService.updatePostViewCount(postId);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // 수정
    // 2. 특정 카테고리의 모든 게시물 상태 변경
    @PatchMapping("/posts-status")
    public ResponseEntity<List<Post>> updatePostStatus() {
        List<Post> postList = postsService.updatePostStatus();
        if(postList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    // 수정
    // 3. 게시물의 상태에 따른 카테고리 변경
    @PatchMapping("/posts-category")
    public ResponseEntity<List<PostCategories>> updatePostCategory() {
        List<PostCategories> postCategoriesList = postsService.updatePostCategory();
        if(postCategoriesList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(postCategoriesList, HttpStatus.OK);
    }

    // 삭제
    // 6. 특정 상태의 게시물 삭제
    @DeleteMapping
    public ResponseEntity<String> deletePostStatus() {
        if(postsService.deletePostStatus() < 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }

    // 삭제
    // 8. 조회수가 낮은 게시물 삭제
    @DeleteMapping("/low-view-count")
    public ResponseEntity<String> deletePostLowViewCount() {
        if(postsService.deletePostLowViewCount() < 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }

    // 삭제
    // 9. 특정 날짜 이전에 작성된 게시물과 그 댓글 삭제
    @DeleteMapping("/{date}/low-view-count")
    public ResponseEntity<String> deletePostLowViewCount(@PathVariable String date) {
        if(postsService.deletePoseByDate(date) < 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }

    // 삭제
    // 10. 다수의 게시물 삭제
    @DeleteMapping("/many-posts")
    public ResponseEntity<String> deleteManyPosts(@RequestBody PostIdListForm postIdListForm) {
        if(postsService.deleteManyPosts(postIdListForm) < 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }




    // 게시물 조회 (GET)
    @GetMapping("/{postId}") //같은 경로를 사용하더라도 http mathod로 오버로딩 가능
    public ResponseEntity<Post> getPost(@PathVariable(name="postId") Long postId) {
        Post post = postsService.getPostById(postId);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 게시물 수정 (PUT)
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable(name="postId") Long postId, Post updatedPost) {
        int postUpdateCount = postsService.updatePost(postId, updatedPost);
        if (postUpdateCount <= 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 게시물 삭제 (DELETE)
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable(name="postId") Long postId) {
        int postDeleteCount= postsService.deletePost(postId);
        if (postDeleteCount<= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}