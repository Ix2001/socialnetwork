package com.socialnetwork.org.conversation;

import com.socialnetwork.org.userdataconversation.UserDataConversation;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

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

    @ManyToMany()
    @JoinColumn(name = "userdataconversation_id")
    public List<UserDataConversation> conversations;

    public Conversation() {

    }

    public Conversation(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conversation)) return false;
        Conversation that = (Conversation) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
