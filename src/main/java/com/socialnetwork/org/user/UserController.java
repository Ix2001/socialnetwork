package com.socialnetwork.org.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<UserData> getUsers(){
       return userService.getUsers();
    }
    @DeleteMapping("/delete")
    public void delete(UserData userData){
        userService.delete(userData);
    }
    @PostMapping("/register")
    public void save(UserData userData){
        userService.save(userData);
    }
    @PutMapping("/edit")
    public void edit(Long id, UserData userData){
        userService.update(id,userData);
    }
}
