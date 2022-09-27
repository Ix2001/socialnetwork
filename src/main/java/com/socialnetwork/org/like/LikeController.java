package com.socialnetwork.org.like;

import com.socialnetwork.org.conversation.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }
    @GetMapping
    public List<Like> getLikes(){
        return likeService.getLikes();
    }
    @PostMapping("/save")
    public void save(Like like){
        likeService.save(like);
    }
    @DeleteMapping("/delete")
    public void delete(Like like){
        likeService.delete(like);
    }
}
