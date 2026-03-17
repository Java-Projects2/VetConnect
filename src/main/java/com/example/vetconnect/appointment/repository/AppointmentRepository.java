package com.example.vetconnect.appointment.repository;

import com.example.vetconnect.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
