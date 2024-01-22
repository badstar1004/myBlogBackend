package com.example.demo.mapper.comment;

import com.example.demo.domain.Comments;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CommentMapper {

	int deleteCommentsByPost(Long postId);

	List<Comments> getCommentsByPostId(Long postId);

    List<Comments> getCommentsInPostId(List<Long> postIdList);
}