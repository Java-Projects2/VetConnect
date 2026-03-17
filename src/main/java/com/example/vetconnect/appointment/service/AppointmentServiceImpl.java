package com.example.vetconnect.appointment.service;

import com.example.vetconnect.appointment.DTO.AppointmentDto;
import com.example.vetconnect.appointment.DTO.AppointmentResponse;
import com.example.vetconnect.appointment.entity.Appointment;
import com.example.vetconnect.appointment.repository.AppointmentRepository;

import com.example.vetconnect.authentication.JWT.JwtService;
import com.example.vetconnect.clinics.Repository.ClinicRepository;
import com.example.vetconnect.clinics.dto.ClinicSummaryDTO;
import com.example.vetconnect.clinics.enitity.Clinic;
import com.example.vetconnect.pet.entity.Pet;
import com.example.vetconnect.pet.repository.PetRepository;
import com.example.vetconnect.users.Repository.UserRepository;
import com.example.vetconnect.users.dto.UserResponse;
import com.example.vetconnect.users.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PetRepository petRepository;
    private final ClinicRepository clinicRepository;


    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = this.appointmentRepository.findAll();
        return appointments.stream().toList();

    }

    @Override
    public String addAppointment(AppointmentDto request) {
        System.out.println(request.getUser_id());
        System.out.println(request.getVet_id());
        System.out.println(request.getPet_id());
        System.out.println(request.getClinic_id());
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pet pet = petRepository.findById(request.getPet_id())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        User vet = userRepository.findById(request.getVet_id())
                .orElseThrow(() -> new RuntimeException("Vet not found"));

        Clinic clinic = clinicRepository.findById(request.getClinic_id())
                .orElseThrow(() -> new RuntimeException("Clinic not found"));
        Appointment appointment = new Appointment(
                user,
                pet,
                vet,
                clinic,
                request.getAppointmentTime(),
                request.getStatus(),
                request.getNotes()
        );
        appointmentRepository.save(appointment);
        return "appointment created successfully";
    }

}
