package com.ownershiptask.tasks.repositories;

import com.ownershiptask.tasks.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRepositoryImpl implements UserRepository {
    private static final List<User> users = new ArrayList<>();

    @Override
    public boolean save(User user) {
        return users.add(user);
    }

    @Override
    public User deleteById(int id) {
        return users.remove(id);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User getById(int id) {
        return users.get(id);
    }


}
