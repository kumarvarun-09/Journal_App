package com.cc.journalApp.service;

import com.cc.journalApp.models.Users;

import java.util.List;

public interface UserService {
    List<Users> getAllUsers();
    Users getUserById(Long id);
    Users getUserByName(String userName);
    Users createUser(Users user);
    Users updateUser(Users user);
    Users deleteUser(Long id);

}
