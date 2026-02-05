package com.example.vetconnect.clinics.service;

import com.example.vetconnect.authentication.JWT.JwtService;
import com.example.vetconnect.authentication.JWT.JwtUserPrincipal;
import com.example.vetconnect.clinics.Repository.ClinicRepository;
import com.example.vetconnect.clinics.dto.ClinicResponseDTO;
import com.example.vetconnect.clinics.dto.CreateClinicDto;
import com.example.vetconnect.clinics.dto.VetDto;
import com.example.vetconnect.clinics.enitity.Clinic;
import com.example.vetconnect.users.Repository.UserRepository;
import com.example.vetconnect.users.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.server.ui.OneTimeTokenSubmitPageGeneratingWebFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClinicServiceImpl implements ClinicService {
    private final ClinicRepository clinicRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public Clinic createClinic(CreateClinicDto dto) {
        JwtUserPrincipal userDataFromToken = jwtService.getUserDataFromToken();
        if (!userDataFromToken.getRole().name().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Admins can create clinics");
        }
        Clinic clinic = new Clinic(
                dto.getName(),
                dto.getAddress(),
                dto.getAddress()
        );
        if (clinicRepository.existsByName(dto.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Clinic name already exists"
            );
        }

        Clinic savedClinic = clinicRepository.save(clinic);
        if (dto.getVetIds() != null && !dto.getVetIds().isEmpty()) {

            List<User> users = userRepository.findAllById(dto.getVetIds());

            if (users.size() != dto.getVetIds().size()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "One or more users not found"
                );
            }
            // make sure that each user is a vet
            for (User user : users) {
                if (user.getRole() != User.Role.vet) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "User with id " + user.getId() + " is not a vet"
                    );
                }
            }

            users.forEach(user -> user.setClinic(savedClinic));

            userRepository.saveAll(users);
        }

        return savedClinic;
    }

    public Page<ClinicResponseDTO> getAllClinics(Pageable pageable) {
        Page<Clinic> clinics = clinicRepository.findAll(pageable);
        return clinics.map(clinic -> {
            ClinicResponseDTO dto = new ClinicResponseDTO();
            dto.setId(clinic.getId());
            dto.setName(clinic.getName());
            dto.setAddress(clinic.getAddress());
            dto.setPhone(clinic.getPhone());
            dto.setVets(clinic.getVets().stream().map(vet -> new VetDto(vet.getId(), vet.getName())).collect(Collectors.toList()));
            dto.setCreatedAt(clinic.getCreatedAt());
            dto.setUpdatedAt(clinic.getUpdatedAt());
            return dto;
        });
    }

    public ClinicResponseDTO getSingleClinic(Long id) {
        Clinic clinic = clinicRepository.findById(id).orElseThrow(()-> new RuntimeException("clinic not found"));
        ClinicResponseDTO dto = new ClinicResponseDTO();
        dto.setId(clinic.getId());
        dto.setName(clinic.getName());
        dto.setAddress(clinic.getAddress());
        dto.setPhone(clinic.getPhone());
        dto.setVets(clinic.getVets().stream().map(vet -> new VetDto(vet.getId(), vet.getName())).collect(Collectors.toList()));
        dto.setCreatedAt(clinic.getCreatedAt());
        dto.setUpdatedAt(clinic.getUpdatedAt());
        return dto;
    }
/*
    public Clinic updateClinic(UpdateClinicDto dto, Long id) {
        Boolean isAdmin = jwtService.isUserAdminOrOwner(dto.getVetId());

        if (!isAdmin) {
            throw new RuntimeException("not authorized");
        }

        Clinic clinic = clinicRepository.findById(id).orElseThrow(() ->
                new RuntimeException("clinic not found")
        );
        if (dto.getName() != null) {
            clinic.setName(dto.getName());
        }
        if (dto.getAddress() != null) clinic.setAddress(dto.getAddress());
        if (dto.getPhone() != null) clinic.setPhone(dto.getPhone());
        if (dto.getVetId() != null) {
            clinic.setVet(userRepository.findById(dto.getVetId()).orElseThrow(() ->
                    new RuntimeException("vet not found")
            ));

        }
        return clinic;
    }*/

}
