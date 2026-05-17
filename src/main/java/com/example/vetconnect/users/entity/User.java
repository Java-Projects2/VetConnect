package com.example.vetconnect.users.entity;

import com.example.vetconnect.clinics.enitity.Clinic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "user", schema = "vetconnect")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    public enum Role {
        user,
        vet,
        admin
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.user; // ✅ DEFAULT HERE

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "clinic_id", nullable = true)
    private Clinic clinic;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}