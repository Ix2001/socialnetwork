package com.socialnetwork.org.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void createPerson(@RequestBody UserData userData) {
        userService.createUser(userData);
    }

    @GetMapping
    public UserPublicDTO getPerson(Authentication authentication) {
        log.info("Getting person by username");
        if (authentication == null) {
            log.info("Authentication is null");
            return null;
        }
        log.info("Authentication is not null");
        return userService.getPersonByUsername(authentication.getName());
    }

    @GetMapping("/followers/{page}")
    public List<UserBasicPublicDTO> getFollowers(Authentication authentication, @PathVariable int page) {
        return userService.getFollowers(authentication.getName(), page);
    }

    @GetMapping("/following/{page}")
    public List<UserBasicPublicDTO> getFollowing(Authentication authentication, @PathVariable int page) {
        return userService.getFollowing(authentication.getName(), page);
    }
    @PatchMapping("/follow/{username}")
    public void followPerson(Authentication authentication, @PathVariable String username) {
        userService.followPerson(authentication.getName(), username);
    }

    @GetMapping("/{username}")
    public UserPublicDTO getPersonByUsername(@PathVariable String username) {
        return userService.getPersonByUsername(username);
    }
    @GetMapping("/search/{username}/{page}")
    public List<UserBasicPublicDTO> searchPersonByUsernameContaining(@PathVariable String username, @PathVariable int page) {
        return userService.searchPersonByUsernameContaining(username, page);
    }

    @PutMapping
    public UserPublicDTO updatePerson(Authentication authentication, @RequestBody UserPublicDTO UserPublicDTO) {
        return userService.updatePerson(authentication.getName(), UserPublicDTO);
    }

    @DeleteMapping
    public void deletePerson(Authentication authentication) {
        userService.deletePerson(authentication.getName());
    }

    @PatchMapping("/upload-profile-picture")
    public void uploadProfilePicture(Authentication authentication, @RequestBody UserBasicPublicDTO base64) {
        userService.uploadProfilePicture(authentication.getName(), base64);
    }

    @PatchMapping("/i-am-teapot")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public void iAmTeapot() {
        log.info("I am teapot");
    }
}
