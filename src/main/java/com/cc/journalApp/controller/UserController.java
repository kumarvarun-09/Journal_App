package com.cc.journalApp.controller;

import com.cc.journalApp.dto.UserDTO;
import com.cc.journalApp.exceptions.UserNotFoundException;
import com.cc.journalApp.request.UserRequest;
import com.cc.journalApp.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDTO> userDTOS = new ArrayList<>();
            userService.getAllUsers()
                    .forEach(user -> userDTOS.add(new UserDTO(user)));
            return new ResponseEntity<>(userDTOS, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserRequest request) {
        try {
            UserDTO user = new UserDTO(userService.updateUser(request));
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        try {
            userService.deleteUser();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
