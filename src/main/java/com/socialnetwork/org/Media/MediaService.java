package com.socialnetwork.org.Media;

import com.socialnetwork.org.like.Like;
import com.socialnetwork.org.like.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;

    @Autowired
    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }
    public List<Media> getMedias(){
        return mediaRepository.findAll();
    }
    public void delete(Media media){
        mediaRepository.delete(media);
    }
    public void save(Media media){
        mediaRepository.save(media);
    }
    @Transactional
    public void update(Long id, Media updatedMedia){
        Media currentMedia = mediaRepository.findById(id).get();
        currentMedia.setPath(updatedMedia.getPath());
        currentMedia.setPostId(updatedMedia.getPostId());
    }
}
