package com.socialnetwork.org.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getComments(){
        return commentService.getComments();
    }
    @PostMapping("/save")
    public void save(Comment comment){
        commentService.save(comment);
    }
    @DeleteMapping("/delete")
    public void delete(Comment comment){
        commentService.delete(comment);
    }
    @PutMapping("/edit")
    public void edit(Long id, Comment comment){
        commentService.update(id, comment);
    }
}
