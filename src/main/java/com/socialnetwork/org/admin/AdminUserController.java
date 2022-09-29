package com.socialnetwork.org.admin;

import com.socialnetwork.org.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/user")
public class AdminUserController {
    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/ban/{username}")
    public void banPerson(@PathVariable String username) {
        userService.banPerson(username);
    }
    @PostMapping("/unban/{username}")
    public void unbanPerson(@PathVariable String username) {
        userService.unbanPerson(username);
    }
    @DeleteMapping("/delete/{username}")
    public void deletePerson(@PathVariable String username) {
        userService.deletePerson(username);
    }
    @PostMapping("/undelete/{username}")
    public void undeletePerson(@PathVariable String username) {
        userService.undeletePerson(username);
    }
    @PatchMapping("/make-admin/{username}")
    public void makeAdmin(@PathVariable String username) {
        userService.makeAdmin(username);
    }
    @PatchMapping("/make-user/{username}")
    public void makeUser(@PathVariable String username) {
        userService.makeUser(username);
    }
    @GetMapping("/statistics/most-popular")
    public void getMostPopular() {
        userService.getMostPopular();
    }

}
