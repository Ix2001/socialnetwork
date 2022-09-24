package com.socialnetwork.org.user;

import com.socialnetwork.org.comment.Comment;
import com.socialnetwork.org.conversation.Conversation;
import com.socialnetwork.org.like.Like;
import com.socialnetwork.org.post.Post;
import lombok.Data;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@Table(name = "user_data")
public class UserData {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;
    @Column(name =
            "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;
    @Column(name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;
    @Column(
            name = "dob",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate dob;

    @ManyToMany
            @JoinTable(name = "user_data_conversation",
                    joinColumns = @JoinColumn(name ="user_data_id"),
                    inverseJoinColumns = @JoinColumn(name = "conversation_id"))
    List<Conversation> conversations;

    @OneToMany(mappedBy = "user")
    List<Post> posts;

    @OneToMany(mappedBy = "userLike")
    private List<Like> likes;

    @OneToMany(mappedBy = "userId")
    private List<Comment> comments;
}
