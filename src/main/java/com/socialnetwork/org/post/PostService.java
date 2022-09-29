package com.socialnetwork.org.post;

import com.socialnetwork.org.comment.Comment;
import com.socialnetwork.org.comment.CommentPublicDTO;
import com.socialnetwork.org.exceptions.UserAlreadyExistException;
import com.socialnetwork.org.exceptions.UserDoesNotExistException;
import com.socialnetwork.org.followers.Followers;
import com.socialnetwork.org.like.PostLike;
import com.socialnetwork.org.like.PostLikeRepository;
import com.socialnetwork.org.message.Message;
import com.socialnetwork.org.pictures.PostPhotoes;
import com.socialnetwork.org.pictures.PostPhotoesRepository;
import com.socialnetwork.org.user.UserBasicPublicDTO;
import com.socialnetwork.org.user.UserData;
import com.socialnetwork.org.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostPhotoesRepository postPhotosRepository;
    private final PostLikeRepository postLikeRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostPhotoesRepository postPhotosRepository, PostLikeRepository postLikeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postPhotosRepository = postPhotosRepository;
        this.postLikeRepository = postLikeRepository;
    }

    public UserBasicPublicDTO getPersonBasicByUsername(String username) {
        UserData person = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        UserBasicPublicDTO personBasicPublicDTO = new UserBasicPublicDTO();
        BeanUtils.copyProperties(person, personBasicPublicDTO);
        return personBasicPublicDTO;
    }

    public PostPublicDTO postDTOtoPost(Post post) {
        PostPublicDTO postDTO = new PostPublicDTO();
        BeanUtils.copyProperties(post, postDTO);
        postDTO.setAuthor(getPersonBasicByUsername(post.getAuthor().getUsername()));
        List<Comment> comments = post.getComments();
        List<CommentPublicDTO> commentsPublicDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            CommentPublicDTO commentsPublicDTO = new CommentPublicDTO();
            BeanUtils.copyProperties(comment, commentsPublicDTO);
            commentsPublicDTO.setAuthorOfComment(getPersonBasicByUsername(comment.getUserId().getUsername()));
            commentsPublicDTOs.add(commentsPublicDTO);
        }
        postDTO.setComments(commentsPublicDTOs);
        return postDTO;
    }



    public List<PostPublicDTO> getFollowingPosts(String name, Integer page) {
        PageRequest pageable = PageRequest.of(page, 10);
        UserData person = userRepository.findByUsername(name).orElseThrow(() -> new UserDoesNotExistException("User not found"));
        List<Followers> followers = person.getFollowing();
        List<PostPublicDTO> postDTOs = new ArrayList<>();
        for (Followers follower : followers) {
            List<Post> posts = postRepository.findByAuthor_Username_OrderByCreationDateDesc(follower.getTo().getUsername());
            for (Post post : posts) {
                postDTOs.add(postDTOtoPost(post));
            }
        }
        return postDTOs;
    }



    public void registerPost(PostRegisterDTO postRegisterDTO, String username) {
        log.info("Registering post: {}", postRegisterDTO);
        UserData person = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        Post post = new Post();
        post.setAuthor(person);
        post.setCreationDate(LocalDate.now());
        post.setText(postRegisterDTO.getText());
        postRepository.save(post);
        List<String> base64Photos = postRegisterDTO.getBase64Photos();
        for (String base64Photo : base64Photos) {
            String[] base64Array = base64Photo.split("[:/;]");
            String type = base64Array[1];
            if (type.equals("image")) {
                String rootLocation = "src/main/resources/static/content/" + username + "/post/images/";
                loadContent(username, base64Photo, rootLocation, post);
            }
            else if (type.equals("video")) {
                String rootLocation = "src/main/resources/static/content/" + username + "/post/videos/";
                loadContent(username, base64Photo, rootLocation, post);
            }
            else {
                log.error("Wrong type of file");
                throw new RuntimeException("Wrong type of file");
            }
        }
    }

    private void loadContent(String username, String base64, String rootLocation, Post post) {
        Path path = Paths.get(rootLocation);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String filename = rootLocation + username + "." + System.currentTimeMillis() + ".txt";
        PostPhotoes postPhotos = new PostPhotoes();
        postPhotos.setPhotoPath(filename);
        postPhotos.setDateOfPhoto(LocalDateTime.now());
        postPhotos.setPost(post);
        postPhotosRepository.save(postPhotos);
        try {
            PrintWriter writer = new PrintWriter(filename, UTF_8);
            writer.println(base64);
            writer.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new UserAlreadyExistException("Post not found"));
        postRepository.delete(post);
    }

    public void editPost(Long id, String text) {
        Post post = postRepository.findById(id).orElseThrow(() -> new UserAlreadyExistException("Post not found"));
        post.setText(text);
        postRepository.save(post);
    }

    public void getMostPopular() {
        ;
    }



    public List<PostPublicDTO> getPostsByUsername(String name, Integer page) {
        List<Post> posts = postRepository.findByAuthor_Username_OrderByCreationDateDesc(name);
        List<PostPublicDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            postDTOs.add(postDTOtoPost(post));
        }
        return postDTOs;
    }

    public void likePost(String name, Long id) {
        UserData person = userRepository.findByUsername(name).orElseThrow(() -> new UserDoesNotExistException("User not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new UserAlreadyExistException("Post not found"));
        PostLike postLike = new PostLike();
        postLike.setAuthorOfLike(person);
        postLike.setPostLike(post);
        postLikeRepository.save(postLike);
    }
}