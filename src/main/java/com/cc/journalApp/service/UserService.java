package com.cc.journalApp.service;

import com.cc.journalApp.exceptions.ResourceNotFoundException;
import com.cc.journalApp.models.User;
import com.cc.journalApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                return user;
            }
            throw new ResourceNotFoundException("User with id: " + id + " not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByName(String userName) {
        try {
            User user = userRepository.findByUserName(userName);
            if (user != null) {
                return user;
            }
            throw new ResourceNotFoundException("User with name: " + userName + " not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User deleteUser(Long id) {
        return null;
    }
}
