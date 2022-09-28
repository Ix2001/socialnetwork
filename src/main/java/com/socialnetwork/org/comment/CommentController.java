package com.socialnetwork.org.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}")
    public void postComment(@PathVariable Long id, @RequestBody Comment comments, Authentication authentication) {
        commentService.postComment(id, comments, authentication.getName());
    }
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id, Authentication authentication) {
        commentService.deleteComment(id, authentication.getName());
    }
    @PatchMapping("/{id}")
    public void updateComment(@PathVariable Long id, @RequestBody Comment comments, Authentication authentication) {
        commentService.updateComment(id, comments, authentication.getName());
    }
}
