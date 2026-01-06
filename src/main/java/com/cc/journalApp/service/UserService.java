package com.cc.journalApp.service;

import com.cc.journalApp.exceptions.ResourceNotFoundException;
import com.cc.journalApp.exceptions.UserNameAlreadyInUseException;
import com.cc.journalApp.exceptions.UserNotFoundException;
import com.cc.journalApp.models.User;
import com.cc.journalApp.repository.UserRepository;
import com.cc.journalApp.request.UserRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

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
    public User getUserByUserName(String userName) throws Exception {
        try {
            User user = userRepository.findByUserName(userName);
            if (user != null) {
                return user;
            }
            throw new UserNotFoundException(userName);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public User createUser(UserRequest request) {
        try {
            if (request.getUserName() == null || request.getUserName().isEmpty()) {
                throw new IllegalArgumentException("Parameter userName can not be empty");
            } else if (request.getPassword() == null || request.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Parameter password can not be empty");
            }
            User user = userRepository.findByUserName(request.getUserName());
            if (user != null) {
                throw new UserNameAlreadyInUseException(user.getUserName());
            }
            User userToSave = new User(request);
            userToSave.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            userToSave.setRoles(List.of("USER"));
            return userRepository.save(userToSave);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (UserNameAlreadyInUseException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public User updateUser(UserRequest request) {
        try {
            if (request.getUserName() == null || request.getUserName().isEmpty()) {
                throw new IllegalArgumentException("Parameter userName can not be empty");
            } else if (request.getPassword() == null || request.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Parameter password can not be empty");
            }
            User user = userRepository.findByUserName(request.getUserName());
            if (user == null) {
                throw new UserNotFoundException(request.getUserName());
            }
            user.setUserName(request.getUserName());
            user.setPassword(request.getPassword());
            return userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public User deleteUser(Long id) {
        User u = userRepository.findById(id).orElse(null);
        userRepository.deleteById(id);
        return u;
    }
}
