package com.socialnetwork.org.conversation;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ConversationService {
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
    public void update(Long id, Conversation updatedConversation){
        Conversation currentConversation = conversationRepository.findById(id).get();
        currentConversation.setId(updatedConversation.getId());
    }
}
