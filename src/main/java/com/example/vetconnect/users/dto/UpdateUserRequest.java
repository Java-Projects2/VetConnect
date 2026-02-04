package com.example.vetconnect.users.dto;

import com.example.vetconnect.clinics.enitity.Clinic;
import com.example.vetconnect.users.entity.User;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private String name;
    private String email;
    private User.Role role;

    private Long clinic_id;
}
