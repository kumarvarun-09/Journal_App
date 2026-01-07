package com.cc.journalApp.service;

import com.cc.journalApp.models.User;
import com.cc.journalApp.request.UserRequest;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUserName(String userName) throws Exception;

    User createUser(UserRequest user);

    User updateUser(UserRequest user);

    void deleteUser();

}
