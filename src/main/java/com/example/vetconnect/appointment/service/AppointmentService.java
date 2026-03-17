package com.example.vetconnect.appointment.service;

import com.example.vetconnect.appointment.DTO.AppointmentDto;
import com.example.vetconnect.appointment.DTO.AppointmentResponse;
import com.example.vetconnect.appointment.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getAllAppointments();
    String addAppointment(AppointmentDto request);
}
