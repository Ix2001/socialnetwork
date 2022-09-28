package com.socialnetwork.org.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND, reason = "User does not exist")
public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException(String username){
        log.error("User does not exist " + username);
    }
}