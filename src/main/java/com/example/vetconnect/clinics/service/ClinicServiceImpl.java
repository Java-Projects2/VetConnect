package com.example.vetconnect.clinics.service;

import com.example.vetconnect.authentication.JWT.JwtService;
import com.example.vetconnect.authentication.JWT.JwtUserPrincipal;
import com.example.vetconnect.clinics.Repository.ClinicRepository;
import com.example.vetconnect.clinics.dto.CreateClinicDto;
import com.example.vetconnect.clinics.dto.ResponseClinicDto;
import com.example.vetconnect.clinics.dto.UpdateClinicDto;
import com.example.vetconnect.clinics.enitity.Clinic;
import com.example.vetconnect.users.Repository.UserRepository;
import com.example.vetconnect.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClinicServiceImpl implements ClinicService {
    private final ClinicRepository clinicRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public ClinicServiceImpl(ClinicRepository clinicRepository, JwtService jwtService, UserRepository userRepository) {
        this.clinicRepository = clinicRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public Clinic createClinic(CreateClinicDto dto) {
        JwtUserPrincipal userDataFromToken = jwtService.getUserDataFromToken();
        if (!userDataFromToken.getRole().name().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "admin only create clinic");
        }
        Set<User> vetEntities = dto.getVets().stream().map(id -> {
            User vet = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vet not found"));
            if (!vet.getRole().name().equals("vet")) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "this user is not vet");
            }
            return vet;
        }).collect(Collectors.toSet());

        Clinic clinic = new Clinic();
        clinic.setName(dto.getName());
        clinic.setAddress(dto.getAddress());
        clinic.setPhone(dto.getPhone());
        clinic.setVets(vetEntities);

        return clinicRepository.save(clinic);
    }

 /*   public Page<ResponseClinicDto> getAll(Pageable pageable) {
        Page<Clinic> clinics = clinicRepository.findAll(pageable);
        return clinics.map(clinic -> {
            ResponseClinicDto dto = new ResponseClinicDto();
            dto.setId(clinic.getId());
            dto.setName(clinic.getName());
            dto.setAddress(clinic.getAddress());
            dto.setPhone(clinic.getPhone());
            dto.setVetName(clinic.getVet().getName());
            return dto;
        });
    }

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
