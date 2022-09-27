package com.socialnetwork.org.user;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginDTO {
    private String username;
    private String password;
}
