package com.socialnetwork.org.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(name = "/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<UserData> getUsers(){
       return userService.getUsers();
    }
    @GetMapping("/delete")
    public void delete(UserData userData){
        userService.delete(userData);
    }
    @GetMapping("/save")
    public void save(UserData userData){
        userService.save(userData);
    }
}
