package com.socialnetwork.org.user;

import com.socialnetwork.org.comment.Comment;
import com.socialnetwork.org.conversation.Conversation;
import com.socialnetwork.org.followers.Followers;
import com.socialnetwork.org.post.Post;
import lombok.*;


import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
    @NonNull
    private String firstName;
    @Column(name =
            "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NonNull
    private String lastName;
    @Column(name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NonNull
    private String email;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NonNull
    private String password;
    @Column(
            name = "dob",
            nullable = false,
            columnDefinition = "DATE"
    )
    @NonNull
    private LocalDate dob;

    @ManyToMany
    @JoinTable(name = "user_data_conversation",
            joinColumns = @JoinColumn(name = "user_data_id"),
            inverseJoinColumns = @JoinColumn(name = "conversation_id"))
    List<Conversation> conversations;

    @OneToMany(mappedBy = "user")
    List<Post> posts;


    @OneToMany(mappedBy = "userId")
    private List<Comment> comments;
    @OneToMany(mappedBy = "from", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Followers> following;
    @OneToMany(mappedBy = "to", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Followers> followers;
}
