package com.socialnetwork.org.conversation;

import com.socialnetwork.org.message.Message;
import com.socialnetwork.org.user.UserData;
import lombok.Data;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;
@Data
@Entity
@Table(name = "conversation")
public class Conversation {
    @Id
    @SequenceGenerator(
            name = "conversation_sequence",
            sequenceName = "conversation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "conversation_sequence"
    )
    private Long id;

    @ManyToMany(mappedBy = "conversations")
    @Column(name = "users")
    public List<UserData> users;
    @OneToMany(mappedBy = "conversation")
            @Column(name = "messages")
    List<Message> messages;
}
