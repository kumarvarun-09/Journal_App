package com.cc.journalApp.dto;

import com.cc.journalApp.models.Users;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String userName;
    public  UserDTO(Users user) {
        this.id = user.getId();
        this.userName = user.getUserName();
    }
}
