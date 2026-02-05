package com.example.vetconnect.clinics.dto;

import com.example.vetconnect.users.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class ClinicResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private List<VetDto> vets;
    private Instant createdAt;
    private Instant updatedAt;
}
