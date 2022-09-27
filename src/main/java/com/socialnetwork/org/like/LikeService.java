package com.socialnetwork.org.like;

import com.socialnetwork.org.conversation.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }
    public List<Like> getLikes(){
        return likeRepository.findAll();
    }
    public void delete(Like like){
        likeRepository.delete(like);
    }
    public void save(Like like){
        likeRepository.save(like);
    }
    @Transactional
    public void update(Long id, Like updatedLike){
        Like currentLike = likeRepository.findById(id).get();
        currentLike.setPostLike(updatedLike.getPostLike());
    }
}
