package com.socialnetwork.org.message;

import com.socialnetwork.org.User.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping()
    public List<Message> getMessages(){
        return messageService.getMessages();
    }
    @GetMapping("/delete")
    public void delete(Message message){
        messageService.delete(message);
    }
    @GetMapping("/save")
    public void save(Message message){
        messageService.save(message);
    }
}
