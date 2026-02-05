package com.example.vetconnect.clinics.service;

import com.example.vetconnect.authentication.JWT.JwtService;
import com.example.vetconnect.authentication.JWT.JwtUserPrincipal;
import com.example.vetconnect.clinics.Repository.ClinicRepository;
import com.example.vetconnect.clinics.dto.*;
import com.example.vetconnect.clinics.enitity.Clinic;
import com.example.vetconnect.users.Repository.UserRepository;
import com.example.vetconnect.users.entity.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
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
        Clinic clinic = clinicRepository.findById(id).orElseThrow(() -> new RuntimeException("clinic not found"));
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

    public void updateClinic(UpdateClinicDto request, Long id) {
        JwtUserPrincipal userDataFromToken = jwtService.getUserDataFromToken();
        if (!userDataFromToken.getRole().name().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Admins can update clinics");
        }
        Clinic clinic = clinicRepository.findById(id).orElseThrow(() -> new RuntimeException("clinic not found"));

        if (request.getName() != null) {

            clinic.setName(request.getName());
        }
        if (request.getAddress() != null) {
            clinic.setAddress(request.getAddress());
        }
        if (request.getPhone() != null) {
            clinic.setPhone(request.getPhone());
        }

        try {
            clinicRepository.save(clinic);
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new RuntimeException("Clinic " + request.getName() + " already exists");
            }
        }
    }

    public void addVetsToClinic(Long id, UpdateVetsDto request) {
        JwtUserPrincipal userDataFromToken = jwtService.getUserDataFromToken();
        if (!userDataFromToken.getRole().name().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Admins can update clinics");
        }
        Clinic clinic = clinicRepository.findById(id).orElseThrow(() -> new RuntimeException("clinic not found"));
        List<User> vetsToAdd = userRepository.findAllById(request.getVets());
        Set<Long> foundIds = vetsToAdd.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        List<Long> notFoundIds = request.getVets().stream()
                .filter(vetId -> !foundIds.contains(vetId))
                .toList();

        if (!notFoundIds.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "The following vet IDs were not found: " + notFoundIds
            );
        }
        vetsToAdd.forEach(vet -> {
            if (!vet.getRole().name().equals("vet")) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "User with ID " + vet.getId() + " is not a vet"
                );
            }
        });
        vetsToAdd.forEach(vet -> vet.setClinic(clinic));
        userRepository.saveAll(vetsToAdd);
    }

    public void removeVetsFromClinic(Long id, UpdateVetsDto request) {
        JwtUserPrincipal userDataFromToken = jwtService.getUserDataFromToken();
        if (!userDataFromToken.getRole().name().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Admins can update clinics");
        }
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found"));
        List<User> vetsToRemove = userRepository.findAllById(request.getVets());

        Set<Long> foundIds = vetsToRemove.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        List<Long> notFoundIds = request.getVets().stream()
                .filter(vetId -> !foundIds.contains(vetId))
                .toList();
        if (!notFoundIds.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "The following vet IDs were not found: " + notFoundIds
            );
        }

        vetsToRemove.forEach(vet -> {
            if (!vet.getRole().name().equals("vet")) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "User with ID " + vet.getId() + " is not a vet"
                );
            }
            vet.setClinic(null);
        });

        userRepository.saveAll(vetsToRemove);


    }

}
