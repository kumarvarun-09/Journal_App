package com.cc.journalApp.exceptions;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String userName) {
        super(
                "User with userName " + userName + " not found"
        );
    }
}
