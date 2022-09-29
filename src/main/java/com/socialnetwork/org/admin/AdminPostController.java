package com.socialnetwork.org.admin;

import com.socialnetwork.org.post.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/post")
public class AdminPostController {

    private final PostService postService;

    @Autowired
    public AdminPostController(PostService postService) {
        this.postService = postService;
    }
    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
    @PatchMapping("/edit/{id}")
    public void editPost(@PathVariable Long id, @RequestBody String text) {
        postService.editPost(id, text);
    }
    @GetMapping("/statistics/most-popular")
    public void getMostPopular() {
        postService.getMostPopular();
    }
}
