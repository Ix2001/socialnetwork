package com.socialnetwork.org.post;

import com.socialnetwork.org.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts(){
        return postRepository.findAll();
    }
    public void delete(Post post){
        postRepository.delete(post);
    }
    public void save(Post post){
        postRepository.save(post);
    }
    @Transactional
    public void update(Long id, Post updatedPost){
        Post currentPost = postRepository.findById(id).get();
        currentPost.setDescription(updatedPost.getDescription());
        currentPost.setCreationDate(updatedPost.getCreationDate());
    }
}
