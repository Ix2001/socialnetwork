package com.socialnetwork.org.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Username Not Found")
public class AuthException extends RuntimeException{
    public AuthException(String username){
        super("User with username: " + username + "not found");
    }
}
