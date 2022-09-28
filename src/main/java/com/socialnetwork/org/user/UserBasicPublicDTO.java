package com.socialnetwork.org.user;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserBasicPublicDTO {
    private String username;
    private String name;
    private String surname;
    private String profilePicture;
}
