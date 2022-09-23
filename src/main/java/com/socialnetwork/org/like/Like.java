package com.socialnetwork.org.like;

import com.socialnetwork.org.post.Post;
import com.socialnetwork.org.user.UserData;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

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

    public Like(Long id, Post postLike, UserData userLike) {
        this.id = id;
        this.postLike = postLike;
        this.userLike = userLike;
    }

    public Like(Post postLike, UserData userLike) {
        this.postLike = postLike;
        this.userLike = userLike;
    }

    public Like() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPostLike() {
        return postLike;
    }

    public void setPostLike(Post postLike) {
        this.postLike = postLike;
    }

    public UserData getUserLike() {
        return userLike;
    }

    public void setUserLike(UserData userLike) {
        this.userLike = userLike;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Like like)) return false;
        return Objects.equals(getId(), like.getId()) && Objects.equals(getPostLike(), like.getPostLike()) && Objects.equals(getUserLike(), like.getUserLike());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPostLike(), getUserLike());
    }

}
