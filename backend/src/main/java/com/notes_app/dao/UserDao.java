package com.notes_app.dao;

import com.notes_app.model.Note;
import com.notes_app.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> getAllUsers();
    Optional<User> getUserByUsername(String username);
    User getUserById(int id);
    User createUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
}
