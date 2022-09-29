package com.socialnetwork.org.comment;

import com.socialnetwork.org.exceptions.UserDoesNotExistException;
import com.socialnetwork.org.post.PostRepository;
import com.socialnetwork.org.user.UserData;
import com.socialnetwork.org.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public void postComment(Long id, Comment comment, String username) {
        UserData userData = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        comment.setUserId(userData);
        comment.setPostId(postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post id")));
        commentRepository.save(comment);
    }

    public void deleteComment(Long id, String username) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid comment id"));
        UserData user = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        if (comment.getUserId().equals(user)) {
            commentRepository.delete(comment);
        } else if (comment.getPostId().getAuthor().equals(user)) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("You are not allowed to delete this comment");
        }
    }

    public void updateComment(Long id, Comment comments, String username) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid comment id"));
        UserData user = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        if (comment.getUserId().equals(user)) {
            comment.setText(comments.getText());
            commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("You are not allowed to update this comment");
        }


    }
}
