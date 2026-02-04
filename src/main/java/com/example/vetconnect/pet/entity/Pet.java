package com.example.vetconnect.pet.entity;

import com.example.vetconnect.appointment.entity.Appointment;
import com.example.vetconnect.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pet", schema = "vetmvc")
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Size(max = 255)
    @NotNull
    @Column(name = "breed", nullable = false)
    private String breed;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "pet")
    private Set<Appointment> appointments = new LinkedHashSet<>();

    public Pet(String name, String type, String breed, Integer age, User user) {
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.age = age;
        this.user = user;
    }
}
