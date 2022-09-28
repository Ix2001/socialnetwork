package com.socialnetwork.org.post;


import com.socialnetwork.org.comment.Comment;
import com.socialnetwork.org.like.PostLike;
import com.socialnetwork.org.pictures.PostPhotoes;
import com.socialnetwork.org.user.UserData;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


import static javax.persistence.GenerationType.SEQUENCE;
@Data
@Entity
@Table(name = "post")
public class Post {
    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "post_sequence"
    )
    private int id;
    @Column(name = "description")
    private String description;
    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserData user;

    @OneToMany(mappedBy = "postLike")
    private List<PostLike> postLikes;

    @OneToMany(mappedBy = "postId")
    private List<Comment> comments;
    @OneToMany(mappedBy = "post")
    private List<PostPhotoes> postPhotoes;
}
