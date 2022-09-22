package com.socialnetwork.org.message;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MessageService {
    private SessionFactory sessionFactory;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    public List<Message> getMessages(){
        return messageRepository.findAll();
    }
    public void delete(Message message){
        messageRepository.delete(message);
    }
    public void save(Message message){
        messageRepository.save(message);
    }
    @Transactional
    public void update(int id, Message updatedMessage){
        Session session = sessionFactory.openSession();
        Message currentMessage = session.get(Message.class,id);
        currentMessage.setText(updatedMessage.getText());
        currentMessage.setRecieverId(updatedMessage.getRecieverId());
        session.close();
    }
}
