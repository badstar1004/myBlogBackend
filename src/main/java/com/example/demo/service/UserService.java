package com.example.demo.service;

import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import com.example.demo.dto.post.PostTitleContentCreatedAtDto;
import com.example.demo.dto.post.PostTitleCreatedAtDto;
import com.example.demo.dto.user.UserPostCount;
import com.example.demo.dto.user.UserPostListDto;
import com.example.demo.mapper.comment.CommentMapper;
import com.example.demo.mapper.post.PostMapper;
import com.example.demo.mapper.postcategories.PostCategoriesMapper;
import com.example.demo.mapper.user.UserMapper;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    PostCategoriesMapper postCategoriesMapper;


    // user의 게시물 조회
    public UserPostListDto getUserPostList(Long userId) {
        List<Post> postList = postService.getPostByUserId(userId);

        return UserPostListDto.builder()
            .userId(userId)
            .userPostList(postList.stream().map(post -> PostTitleCreatedAtDto.builder()
                    .title(post.getTitle())
                    .createdAt(post.getCreatedAt())
                    .build())
                .collect(Collectors.toList()))
            .build();
    }

    // 유저별 게시글 수 조회
    public List<UserPostCount> getUserPostCount() {
        List<User> userList = userMapper.getAllUser();

        List<Post> postList = postService.getPostInUserId(userList.stream()
            .map(User::getUserId).collect(Collectors.toList()));

        // userId를 기준으로 게시물 수 계산
        Map<Long, Long> userPostCountMap =
            postList.stream().collect(Collectors.groupingBy(Post::getUserId, Collectors.counting()));

        // UserPostCount 객체 리스트 생성
        return userList.stream()
            .map(user -> UserPostCount.builder()
                .userId(Math.toIntExact(user.getUserId()))
                .postCount(userPostCountMap.getOrDefault(user.getUserId(), 0L).intValue())
                .build())
            .collect(Collectors.toList());
    }

    // 특정 유저의 최근 3개 게시물
    public List<PostTitleContentCreatedAtDto> getUserPostRecent(Long userId) {
        return postService.getUserPostRecent(userId);
    }

    public List<User> getUsersPaging(Map<String, Object> pagingMap) {
        return userMapper.getUserPaging(pagingMap);
    }

    @Transactional
    public int deleteUser(Long userId) {
        int totalDeleted = 0;

        // 작성한 게시물 조회
        List<Post> postList = postMapper.getPostByUserId(userId);

        if(!postList.isEmpty()) {
            // 작성한 게시물들의 댓글, 대댓글 삭제 및 작성한 댓글, 대댓글 삭제
            List<Long> postIds = postList.stream()
                .map(Post::getPostId)
                .toList();

            totalDeleted += commentMapper.deleteCommentsInPostId(postIds);
            totalDeleted += commentMapper.deleteCommentsByUserId(userId);

            // PostCategories 삭제
            totalDeleted += postCategoriesMapper.deletePostCategoriesInPost(postIds);

            // 게시글 삭제
            totalDeleted += postMapper.deletePostInPostId(postIds);

            // 유저 삭제
            totalDeleted += userMapper.deleteUser(userId);
        }

        return totalDeleted;
    }
}
