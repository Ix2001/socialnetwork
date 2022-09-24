package com.socialnetwork.org.comment;

import com.socialnetwork.org.conversation.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getComments(){
        return commentRepository.findAll();
    }

    public void delete(Comment comment){
        commentRepository.delete(comment);
    }

    public void save(Comment comment){
        commentRepository.save(comment);
    }
    @Transactional
    public void update(Long id, Comment updatedComment){
        Comment currentComment = commentRepository.findById(id).get();
        currentComment.setPostId(updatedComment.getPostId());
        currentComment.setText(updatedComment.getText());
    }
}
