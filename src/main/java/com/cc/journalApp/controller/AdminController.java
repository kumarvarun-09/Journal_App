package com.cc.journalApp.controller;

import com.cc.journalApp.dto.UserDTO;
import com.cc.journalApp.exceptions.UserNameAlreadyInUseException;
import com.cc.journalApp.request.UserRequest;
import com.cc.journalApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDTO> allUsersDTO = new ArrayList<>();
            userService.getAllUsers()
                    .forEach(u -> allUsersDTO.add(new UserDTO(u)));
            return ResponseEntity.ok(allUsersDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("create-admin-user")
    public ResponseEntity<?> createAdminUser(@RequestBody UserRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                            new UserDTO(userService.createAdminUser(request))
                    );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (UserNameAlreadyInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
