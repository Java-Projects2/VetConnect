package com.example.vetconnect.appointment.DTO;

import com.example.vetconnect.clinics.enitity.Clinic;
import com.example.vetconnect.pet.entity.Pet;
import com.example.vetconnect.users.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * DTO for {@link com.example.vetconnect.appointment.entity.Appointment}
 */
@Getter
@Setter
public class AppointmentDto implements Serializable {
    @NotNull
    Date appointmentTime;
    String status;
    String notes;
    @NotNull
    Long user_id;
    @NotNull
    Long pet_id;
    @NotNull
    Long vet_id;
    @NotNull
    Long clinic_id;
}