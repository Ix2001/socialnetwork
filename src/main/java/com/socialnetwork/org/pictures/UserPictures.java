package com.socialnetwork.org.pictures;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socialnetwork.org.user.UserData;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_pictures")
public class UserPictures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photoPath;
    private LocalDateTime dateOfPhoto;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserData user;
}
