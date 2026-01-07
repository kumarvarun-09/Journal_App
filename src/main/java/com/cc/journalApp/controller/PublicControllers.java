package com.cc.journalApp.controller;

import com.cc.journalApp.dto.UserDTO;
import com.cc.journalApp.exceptions.UserNameAlreadyInUseException;
import com.cc.journalApp.request.UserRequest;
import com.cc.journalApp.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicControllers {
    private final IUserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<?> addUser(@RequestBody UserRequest request) {
        try {
            UserDTO user = new UserDTO(userService.createUser(request));
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (UserNameAlreadyInUseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
