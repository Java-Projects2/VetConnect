package com.example.vetconnect.appointment.entity;

import com.example.vetconnect.clinics.enitity.Clinic;
import com.example.vetconnect.pet.entity.Pet;
import com.example.vetconnect.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "appointment", schema = "vetmvc")
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "appointment_time", nullable = false)
    private Date appointmentTime;

/*
    @ColumnDefault("'pending'")
    @Lob
    @Column(name = "status", nullable = false)
    private String status;
*/

    @Lob
    @Column(name = "notes")
    private String notes;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vet_id", nullable = false)
    private User vet;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = "pending";
        }
        if (type == null) {
            type = "checkup";
        }
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "status", nullable = false)
    private String status = "pending";

    @Column(name = "type", nullable = false)
    private String type = "checkup";
    public Appointment(User user, Pet pet, User vet, Clinic clinic, Date appointmentTime, String status, String notes) {
        this.user = user;
        this.pet = pet;
        this.vet = vet;
        this.clinic = clinic;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.notes = notes;
    }
}