package com.example.demo.controller;

import com.example.demo.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    // 삭제
    // 5. 오래된 댓글 삭제
    @DeleteMapping
    public ResponseEntity<String> deleteComments() {
        if(commentsService.deleteComments() <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
     }

    // 삭제
    // 7. 특정 사용자가 작성한 댓글 삭제
    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteUserComments(@PathVariable Long userId) {
        if(commentsService.deleteUserComments(userId) <= 0) {
            return new ResponseEntity<>("대댓글의 작성자가 다릅니다.", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }
}
