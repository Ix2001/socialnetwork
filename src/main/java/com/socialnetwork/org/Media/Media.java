package com.socialnetwork.org.Media;

import com.socialnetwork.org.post.Post;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@Table(name = "media")
public class Media {
    @Id
    @SequenceGenerator(
            name = "media_sequence",
            sequenceName = "like_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "media_sequence"
    )
    private Long id;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;
    @Column(name = "path")
    private String path;

}
