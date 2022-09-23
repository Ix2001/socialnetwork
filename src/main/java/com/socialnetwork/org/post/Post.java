package com.socialnetwork.org.post;


import com.socialnetwork.org.like.Like;
import com.socialnetwork.org.user.UserData;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

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
    private List<Like> likes;

    public Post(int id, String description, Date creationDate) {
        this.id = id;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Post(String description, Date creationDate) {
        this.description = description;
        this.creationDate = creationDate;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return getId() == post.getId() && Objects.equals(getDescription(), post.getDescription()) && Objects.equals(getCreationDate(), post.getCreationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getCreationDate());
    }
}
