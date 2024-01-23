package com.example.demo.mapper.post;

import com.example.demo.domain.Post;
import com.example.demo.dto.post.PostListDto;
import com.example.demo.dto.post.PostTitleContentCreatedAtDto;
import com.example.demo.dto.post.PostTitleCreatedAtDto;
import com.example.demo.dto.post.PostTitleViewCountDto;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PostMapper {

    // 게시물 조회
//    @Select("SELECT * FROM Posts WHERE post_id = #{postId}")
    Post getPostById(Long postId);

	List<Post> getAllPosts();

	List<PostListDto> getPublicPosts();

	List<Post> getPostByUserId(Long userId);

	int createPost(Post post);

	int updatePost(Long postId, Post post);

	int deletePost(Long postId);

	List<PostTitleViewCountDto> getPostTop();

	List<Post> getPostByCategoryId(Long categoryId);

    List<Post> getPostInUserIds(List<Long> userIdList);

    List<PostTitleContentCreatedAtDto> getUserPostRecent(Long userId);

	PostTitleViewCountDto getPopularPostInPostIds(List<Long> postIdList);

    List<PostTitleCreatedAtDto> getPostPeriod();

    List<Post> getPostsPaging(Map<String, Object> pagingMap);

	int updatePostViewCount(Long postId);

    int updatePostStatusInPostIds(List<Long> postIds);

	List<Post> getPostInPostIds(List<Long> postIds);

	List<Post> findPostIdsByStatus(String status);

	int deletePostInPostId(List<Long> postIds);

	List<Post> getPostByViewCount();

    List<Post> getPostByDate(String date);

    // 게시물 목록 조회, 생성, 수정, 삭제 등의 메소드 추가
}