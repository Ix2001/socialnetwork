package com.socialnetwork.org.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Service
public class UserService {
    private SessionFactory sessionFactory;
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
    public void update(int id, UserData updatedUser){
        Session session = sessionFactory.openSession();
        UserData currentUser = session.get(UserData.class,id);
        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setLastName(updatedUser.getLastName());
        currentUser.setFirstName(updatedUser.getFirstName());
        currentUser.setPassword(updatedUser.getPassword());
        session.close();
    }
}
