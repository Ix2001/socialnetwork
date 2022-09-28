package com.socialnetwork.org.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(value = org.springframework.http.HttpStatus.CONFLICT, reason = "User already exists")
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String username){
        log.error("User with username {} already exist", username);
    }
}
