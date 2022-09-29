package com.socialnetwork.org.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{page}")
    public List<PostPublicDTO> getMyPosts(Authentication authentication, @PathVariable Integer page) {
        return postService.getPostsByUsername(authentication.getName(), page);
    }
    @GetMapping("/following/{page}")
    public List<PostPublicDTO> getFollowingPosts(Authentication authentication, @PathVariable Integer page) {
        return postService.getFollowingPosts(authentication.getName(), page);
    }

    @GetMapping("/{username}/{page}")
    public List<PostPublicDTO> getPostsByUsername(@PathVariable String username, @PathVariable Integer page) {
        return postService.getPostsByUsername(username, page);
    }
    @PostMapping
    public void registerPost(@RequestBody PostRegisterDTO postRegisterDTO, Authentication authentication) {
        postService.registerPost(postRegisterDTO, authentication.getName());
    }
    @PatchMapping("/like/{id}")
    public void likePost(Authentication authentication, @PathVariable Long id) {
        postService.likePost(authentication.getName(), id);
    }

}