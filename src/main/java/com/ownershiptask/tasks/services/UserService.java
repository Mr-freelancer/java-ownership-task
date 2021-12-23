package com.ownershiptask.tasks.services;

import com.ownershiptask.tasks.models.User;
import com.ownershiptask.tasks.repositories.UserRepositoryImpl;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService{
    private final UserRepositoryImpl userRepository;

    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user){
        this.userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.getAll();
    }
}
