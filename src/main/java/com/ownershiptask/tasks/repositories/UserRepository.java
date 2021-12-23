package com.ownershiptask.tasks.repositories;
import com.ownershiptask.tasks.models.User;

import java.util.List;

public interface UserRepository {
    boolean save(User user);
    User deleteById(int id);
    List getAll();
    User getById(int id);
}
