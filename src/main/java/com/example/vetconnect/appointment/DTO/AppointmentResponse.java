package com.example.vetconnect.appointment.DTO;

import com.example.vetconnect.clinics.dto.VetDto;
import com.example.vetconnect.clinics.enitity.Clinic;
import com.example.vetconnect.pet.entity.Pet;
import com.example.vetconnect.users.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.example.vetconnect.appointment.entity.Appointment}
 */
@Value
public class AppointmentResponse implements Serializable {
    Long id;
    @NotNull
    Instant appointmentTime;
    @NotNull
    String status;
    String notes;
    @NotNull
    UserSummaryDTO user;
    @NotNull
    Pet pet;
    @NotNull
    VetDto vet;
    @NotNull
    Clinic clinic;
    Instant createdAt;
}