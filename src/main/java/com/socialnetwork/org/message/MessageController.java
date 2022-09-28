package com.socialnetwork.org.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send/{username}")
    public void send(Authentication authentication, @PathVariable String username, @RequestBody Message message) {
        messageService.send(authentication.getName(), username, message);
    }
    @GetMapping("/inbox")
    public List<Message> getAllMessages(Authentication authentication) {
        return messageService.getAllMessages(authentication.getName());
    }
    @DeleteMapping("/delete/{id}")
    public void deleteMyMessage(Authentication authentication, @PathVariable Long id) {
        messageService.deleteMyMessage(authentication.getName(), id);
    }
    @PatchMapping("/edit/{id}")
    public void editMyMessage(Authentication authentication, @PathVariable Long id, @RequestBody Message message) {
        messageService.editMyMessage(authentication.getName(), id, message);
    }
}
