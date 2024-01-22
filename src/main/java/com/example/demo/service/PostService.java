package com.example.demo.service;

import com.example.demo.domain.Categories;
import com.example.demo.domain.Comments;
import com.example.demo.domain.Post;
import com.example.demo.dto.post.PostByCategoriesDto;
import com.example.demo.dto.post.PostCommentCountDto;
import com.example.demo.dto.post.PostDto;
import com.example.demo.dto.post.PostListDto;
import com.example.demo.dto.post.PostTitleContentCreatedAtDto;
import com.example.demo.dto.post.PostTitleCreatedAtDto;
import com.example.demo.dto.post.PostTitleViewCountDto;
import com.example.demo.mapper.comment.CommentMapper;
import com.example.demo.mapper.post.PostMapper;
import com.example.demo.mapper.postcategories.PostCategoriesMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostCategoriesMapper postCategoriesMapper;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private CommentsService commentsService;

    // 게시물 조회 메서드
    public Post getPostById(Long postId) {
        return postMapper.getPostById(postId);
    }

    // 모든 게시물 조회 메서드
    public List<Post> getAllPosts() {
        return postMapper.getAllPosts();
    }

    // 게시물 생성 메서드
    public int createPost(Post post) {
        return postMapper.createPost(post);
    }

    // 게시물 수정 메서드
    public int updatePost(Long postId, Post post) {

        return postMapper.updatePost(post);
    }

    // 게시물 삭제 메서드
    @Transactional // all or not 원칙으로 하나라도 구문이 실패하면 모두 롤백시킵니다.
    public int deletePost(Long postId) {
        commentMapper.deleteCommentsByPost(postId);
        postCategoriesMapper.deletePostCategoriesByPost(postId);
        return postMapper.deletePost(postId);
    }

    // public 게시글 조회
    public List<PostListDto> getPublicPostList() {
        return postMapper.getPublicPosts();
    }

    // userId 기준 게시글 조회
    public List<Post> getPostByUserId(Long userId) {
        return postMapper.getPostByUserId(userId);
    }

    public List<PostTitleViewCountDto> getPostTop() {
        return postMapper.getPostTop();
    }

    // categoryId 기준 게시글 조회
    public PostByCategoriesDto getPostCategoriesByCategoriesId(String categoryName) {
        Categories categories = categoriesService.getCategoriesByName(categoryName);

        List<Post> postList = postMapper.getPostByCategoryId(categories.getCategoryId());
        return PostByCategoriesDto.builder()
            .category_name(categoryName)
            .postList(postList.stream().map(post -> PostDto.builder()
                    .postId(post.getPostId())
                    .userId(post.getUserId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .status(post.getStatus())
                    .viewCount(post.getViewCount())
                    .created_at(post.getCreatedAt())
                    .build())
                .collect(Collectors.toList()))
            .build();
    }

    // List<userId> 기준 게시물 조회 (IN)
    public List<Post> getPostInUserId(List<Long> userIdList) {
        return postMapper.getPostInUserIds(userIdList);
    }

    public List<Comments> getPostAllComments(Long postId) {
        return commentsService.getCommentsByPostId(postId);
    }

    public List<PostTitleContentCreatedAtDto> getUserPostRecent(Long userId) {
        return postMapper.getUserPostRecent(userId);
    }

    // 게시물 별 댓글 수 조회
    public List<PostCommentCountDto> getPostComments() {
        // 게시물 조회
        List<Post> postList = postMapper.getAllPosts();
        List<Comments> commentsList = commentsService.getCommentsInPostId(postList.stream()
            .map(Post::getPostId).collect(Collectors.toList()));

        // postId 기준 댓글 수 계산
        Map<Long, Long> postCommentCountMap =
            commentsList.stream()
                .collect(Collectors.groupingBy(Comments::getPostId, Collectors.counting()));

        return postList.stream()
            .map(post -> PostCommentCountDto.builder()
                .postList(Collections.singletonList(post)) // 현재 게시물만 포함하는 리스트 생성
                .commentCount(postCommentCountMap.getOrDefault(post.getPostId(), 0L).intValue())
                .build())
            .collect(Collectors.toList());
    }

    public List<PostTitleCreatedAtDto> getPostPeriod() {
        return postMapper.getPostPeriod();
    }

    public List<Post> getPostsPaging(Map<String, Object> pagingMap) {
        return postMapper.getPostsPaging(pagingMap);
    }
}


