package com.example.vetconnect.users.dto;


import com.example.vetconnect.clinics.dto.ClinicSummaryDTO;
import com.example.vetconnect.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private User.Role role;
    private ClinicSummaryDTO clinic;
    private Instant created_at;

}
