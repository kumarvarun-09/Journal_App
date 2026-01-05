package com.cc.journalApp.exceptions;

public class UserNameAlreadyInUseException extends RuntimeException {
    public UserNameAlreadyInUseException(String userName) {
        super("User with userName " + userName + " already exists");
    }
}
