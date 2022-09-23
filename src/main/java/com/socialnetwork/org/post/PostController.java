package com.socialnetwork.org.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping
    public List<Post> getPosts(){
        return postService.getPosts();
    }
    @DeleteMapping("/delete")
    public void delete(Post post){
        postService.delete(post);
    }
    @PostMapping("/save")
    public void save(Post post){
        postService.save(post);
    }
    @PostMapping("/edit")
    public void update(Long id, Post post){
        postService.update(id,post);
    }
}
