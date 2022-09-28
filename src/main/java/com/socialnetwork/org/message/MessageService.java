package com.socialnetwork.org.message;

import com.socialnetwork.org.exceptions.UserDoesNotExistException;
import com.socialnetwork.org.user.UserData;
import com.socialnetwork.org.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class MessageService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public void send(String myName, String username, Message message) {
        UserData author = userRepository.findByUsername(myName).orElseThrow(() -> new UserDoesNotExistException(myName));
        UserData recipient = userRepository.findByUsername(username).orElseThrow(() -> new UserDoesNotExistException(username));
        message.setAuthorId(author);
        message.setRecieverId(recipient);
        message.setCreationDate(LocalDate.now());
        message.setIsRead(false);
        messageRepository.save(message);
    }

    public List<Message> getAllMessages(String name) {
        return messageRepository.findAllByAuthorId_UsernameOrRecieverId_UsernameOrderByCreationDateDesc(name, name);
    }

    public void deleteMyMessage(String name, Long id) {
        if (messageRepository.findById(id).get().getAuthorId().getUsername().equals(name)) {
            messageRepository.deleteById(id);
        }
        else {
            throw new IllegalArgumentException("You can't delete this message");
        }
    }

    public void editMyMessage(String name, Long id, Message message) {
        if (messageRepository.findById(id).get().getAuthorId().getUsername().equals(name)) {
            messageRepository.findById(id).get().setText(message.getText());
            messageRepository.save(messageRepository.findById(id).get());
        }
        else {
            throw new IllegalArgumentException("You can't edit this message");
        }
    }
}
