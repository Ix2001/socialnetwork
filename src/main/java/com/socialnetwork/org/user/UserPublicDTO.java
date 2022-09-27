package com.socialnetwork.org.user;

import lombok.*;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPublicDTO {
    private Long id;
    private String username;
    private String profilePicture;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthday;
    private Integer followersCount;
    private Integer followingCount;
}
