package com.socialnetwork.org.conversation;

import com.socialnetwork.org.message.Message;
import com.socialnetwork.org.message.MessageRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ConversationService {
    private SessionFactory sessionFactory;
    private final ConversationRepository conversationRepository;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository) {

        this.conversationRepository = conversationRepository;
    }

    public List<Conversation> getMessages(){
        return conversationRepository.findAll();
    }
    public void delete(Conversation conversation){
        conversationRepository.delete(conversation);
    }
    public void save(Conversation conversation){
        conversationRepository.save(conversation);
    }
    @Transactional
    public void update(int id, Conversation updatedConversation){
        Session session = sessionFactory.openSession();
        Conversation currentConversation = session.get(Conversation.class,id);
        currentConversation.setId(updatedConversation.getId());
        session.close();
    }
}
