package com.example.vetconnect.users.dto;


import com.example.vetconnect.users.entity.User;
import lombok.Getter;

import java.time.Instant;

@Getter
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private com.example.vetconnect.users.entity.User.Role role;
    private Instant created_at;

    public UserResponse(Long id, String name, String email, User.Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
