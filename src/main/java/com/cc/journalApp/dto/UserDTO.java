package com.cc.journalApp.dto;

import com.cc.journalApp.models.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String userName;
    private List<String> roles;

    public UserDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.roles = user.getRoles();
    }
}
