package com.socialnetwork.org.userdataconversation;

import com.socialnetwork.org.User.UserData;
import com.socialnetwork.org.conversation.Conversation;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "user_data_conversation")
public class UserDataConversation {
    @Id
    @SequenceGenerator(
            name = "userdataconversation_sequence",
            sequenceName = "userdataconversation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "userdataconversation_sequence"
    )
    private Long id;
    @ManyToMany
    @JoinColumn(name = "user_data_id")
    private List<UserData> userDataId;
    @ManyToMany(mappedBy = "conversations")
    private List <Conversation> conversationId;

    public UserDataConversation() {
    }

    public UserDataConversation(Long id, List<UserData> userDataId, List<Conversation> conversationId) {
        this.id = id;
        this.userDataId = userDataId;
        this.conversationId = conversationId;
    }

    public UserDataConversation(List<UserData> userDataId, List<Conversation> conversationId) {
        this.userDataId = userDataId;
        this.conversationId = conversationId;
    }
}
