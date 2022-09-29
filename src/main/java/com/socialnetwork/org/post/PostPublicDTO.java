package com.socialnetwork.org.post;

import com.socialnetwork.org.comment.CommentPublicDTO;
import com.socialnetwork.org.user.UserBasicPublicDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostPublicDTO {
    private String text;
    private String picture;
    private LocalDate dateOfPost;
    private UserBasicPublicDTO author;
    private List<CommentPublicDTO> comments;
}
