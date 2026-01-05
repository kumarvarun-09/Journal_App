package com.cc.journalApp.service;

import com.cc.journalApp.exceptions.ResourceNotFoundException;
import com.cc.journalApp.models.User;
import com.cc.journalApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserById(Long id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                return user;
            }
            throw new ResourceNotFoundException("User with id: " + id + " not found");
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByUserName(String userName) throws Exception{
        try {
            User user = userRepository.findByUserName(userName);
            if (user != null) {
                return user;
            }
            throw new ResourceNotFoundException("User with username: " + userName + " not found");
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User deleteUser(Long id) {
        User u = userRepository.findById(id).orElse(null);
        userRepository.deleteById(id);
        return u;
    }
}
