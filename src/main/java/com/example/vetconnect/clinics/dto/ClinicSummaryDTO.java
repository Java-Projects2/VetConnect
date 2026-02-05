package com.example.vetconnect.clinics.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.example.vetconnect.clinics.enitity.Clinic}
 */
@Value
public class ClinicSummaryDTO implements Serializable {
    Long id;
    String name;
    String address;
    String phone;
    Instant createdAt;
    Instant updatedAt;
}