package com.socialnetwork.org.Media;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/media")
public class MediaController {
    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }
    @GetMapping
    public List<Media> getLikes(){
        return mediaService.getMedias();
    }
    @PostMapping("/save")
    public void save(Media media){
        mediaService.save(media);
    }
    @DeleteMapping("/delete")
    public void delete(Media media){
        mediaService.delete(media);
    }
}
