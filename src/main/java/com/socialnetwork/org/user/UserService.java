package com.socialnetwork.org.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserData> getUsers(){
       return userRepository.findAll();
    }
    public void delete(UserData userData){
        userRepository.delete(userData);
    }
    public void save(UserData userData){
        userRepository.save(userData);
    }
    @Transactional
    public void update(Long id, UserData updatedUser){
        UserData currentUser = userRepository.findById(id).get();
        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setLastName(updatedUser.getLastName());
        currentUser.setFirstName(updatedUser.getFirstName());
        currentUser.setPassword(updatedUser.getPassword());
    }
}
