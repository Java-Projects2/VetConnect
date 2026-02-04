package com.example.vetconnect.clinics.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ResponseClinicDto {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String vetName;
    private Instant createdAt;
    private Instant updatedAt;
}
