package com.example.demo.service;

import com.example.demo.domain.Comments;
import com.example.demo.mapper.comment.CommentMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Mapper
public class CommentsService {

    @Autowired
    private CommentMapper commentMapper;

    public List<Comments> getCommentsByPostId(Long postId) {
        return commentMapper.getCommentsByPostId(postId);
    }

    public List<Comments> getCommentsInPostId(List<Long> postIdList) {
        return commentMapper.getCommentsInPostId(postIdList);
    }

    @Transactional
    public int deleteComments() {
        return commentMapper.deleteCommentsByDate();
    }

    @Transactional
    public int deleteUserComments(Long userId) {
        // userId 기준 댓글 조회
        List<Comments> commentsList = commentMapper.getCommentsByUserId(userId);

        List<Long> commentIds = commentsList.stream()
            .map(Comments::getCommentId)
            .toList();

        // userId와 다른 대댓글 있는지 조회
        List<Comments> parentCommentsList =
            commentMapper.getCommentsByUserIdAndInCommentId(userId, commentIds);

        // 대댓글이 있다면 return 0
        if(!parentCommentsList.isEmpty()) {
            return 0;

        } else {
            return commentMapper.deleteCommentsByCommentId(commentIds);
        }
    }
}
