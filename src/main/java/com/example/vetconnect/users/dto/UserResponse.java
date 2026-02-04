package com.example.vetconnect.users.dto;


import com.example.vetconnect.clinics.enitity.Clinic;
import com.example.vetconnect.users.entity.User;
import lombok.Getter;

import java.time.Instant;

@Getter
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private User.Role role;
    private Clinic clinic;
    private Instant created_at;

    public UserResponse(Long id, String name, String email, User.Role role, Clinic clinic) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
