package com.cc.journalApp.service;

import com.cc.journalApp.models.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUserName(String userName) throws Exception;

    User createUser(User user);

    User updateUser(User user);

    User deleteUser(Long id);

}
