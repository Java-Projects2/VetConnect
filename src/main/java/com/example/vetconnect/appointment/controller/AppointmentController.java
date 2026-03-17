package com.example.vetconnect.appointment.controller;

import com.example.vetconnect.appointment.DTO.AppointmentDto;
import com.example.vetconnect.appointment.DTO.AppointmentResponse;
import com.example.vetconnect.appointment.entity.Appointment;
import com.example.vetconnect.appointment.service.AppointmentService;
import com.example.vetconnect.users.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<String> addAppointment(@Valid @RequestBody AppointmentDto request) {
        System.out.println("Adding appointment to database...");
        appointmentService.addAppointment(request);
        return ResponseEntity.ok("Success");
    }
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}
