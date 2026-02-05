package com.example.vetconnect.clinics.enitity;

import com.example.vetconnect.appointment.entity.Appointment;
import com.example.vetconnect.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "clinic", schema = "vetmvc")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Size(max = 11)
    @NotNull
    @Column(name = "phone", nullable = false, length = 11)
    private String phone;

    @OneToMany(mappedBy = "clinic")
    private Set<User> vets = new LinkedHashSet<>();



    @OneToMany(mappedBy = "clinic")
    private Set<Appointment> appointments = new LinkedHashSet<>();


    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public Clinic(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}