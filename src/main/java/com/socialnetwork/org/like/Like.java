package com.socialnetwork.org.like;

import com.socialnetwork.org.post.Post;
import com.socialnetwork.org.user.UserData;
import lombok.Data;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;
@Data
@Entity
@Table(name = "likes")
public class Like {
    @Id
    @SequenceGenerator(
            name = "like_sequence",
            sequenceName = "like_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "like_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_like")
    private Post postLike;

    @ManyToOne
    @JoinColumn(name = "user_like")
    private UserData userLike;

}
