package com.example.vetconnect.clinics.Repository;

import com.example.vetconnect.clinics.enitity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    Optional<Clinic> findById(Long aLong);
    boolean existsByName(String name);
}
