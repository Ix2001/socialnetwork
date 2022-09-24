package com.socialnetwork.org.conversation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/conversation")
public class ConversationController {
    private final ConversationService conversationService;
    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }
    @GetMapping
    public List<Conversation> getConversations(){
        return conversationService.getMessages();
    }
    @PostMapping("/save")
    public void save(Conversation conversation){
        conversationService.save(conversation);
    }
    @DeleteMapping("/delete")
    public void delete(Conversation conversation){
        conversationService.delete(conversation);
    }
    @PutMapping("/edit")
    public void edit(Long id, Conversation conversation){
        conversationService.update(id, conversation);
    }
}
