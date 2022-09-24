package com.socialnetwork.org.message;

import com.socialnetwork.org.conversation.Conversation;
import com.socialnetwork.org.user.UserData;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;
@Data
@Entity
@Table(name = "message")
public class Message {

    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "message_sequence"
    )
    private Long id;
    @Column(name = "text")
    private String text;
    @Column(name = "creation_date")
    private Date creationDate;
/*    @Column(name = "reciever_id")
    private Long recieverId;

    @Column(name = "sender_id")
    private Long senderId;*/

    @ManyToOne
    @JoinColumn(name = "reciever_id")
    private UserData recieverId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserData authorId;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

}
