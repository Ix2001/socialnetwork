package com.socialnetwork.org.comment;

import com.socialnetwork.org.user.UserBasicPublicDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentPublicDTO {
    private Long id;
    private String text;
    private LocalDate dateOfComment;
    private UserBasicPublicDTO authorOfComment;
}
