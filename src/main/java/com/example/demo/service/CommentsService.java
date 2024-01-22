package com.example.demo.service;

import com.example.demo.domain.Comments;
import com.example.demo.mapper.comment.CommentMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
