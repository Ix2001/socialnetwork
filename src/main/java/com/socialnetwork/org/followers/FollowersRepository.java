package com.socialnetwork.org.followers;

import com.socialnetwork.org.user.UserData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowersRepository extends JpaRepository<Followers, Long> {
    List<Followers> findByFrom(UserData from, PageRequest pageRequest);
    List<Followers> findByTo(UserData to, PageRequest pageRequest);

    Followers findByFromAndTo(UserData from, UserData to);
}
