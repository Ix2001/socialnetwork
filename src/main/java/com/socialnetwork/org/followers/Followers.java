package com.socialnetwork.org.followers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socialnetwork.org.user.UserData;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "followers")
public class Followers {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="fromuserdata_id")
    private UserData from;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="touserdata_id")
    private UserData to;
}
