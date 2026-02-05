package com.example.vetconnect.clinics.controller;

import com.example.vetconnect.clinics.dto.ClinicResponseDTO;
import com.example.vetconnect.clinics.dto.CreateClinicDto;
import com.example.vetconnect.clinics.enitity.Clinic;
import com.example.vetconnect.clinics.service.ClinicService;
import com.example.vetconnect.users.Repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/clinics")
@AllArgsConstructor
public class ClinicController {
    private final ClinicService clinicService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> CreateClinic(@Valid @RequestBody CreateClinicDto request) {
        clinicService.createClinic(request);
        return ResponseEntity.ok("clinic crreated successfully");
    }

    @GetMapping
    public ResponseEntity<Page<ClinicResponseDTO>> getAllClinics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<ClinicResponseDTO> clinics = clinicService.getAllClinics(pageable);
        return ResponseEntity.ok(clinics);
    }
/*
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePet(@PathVariable("id") Long id) {
        return new ResponseEntity<>(petService.deletePet(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePet(@PathVariable("id") Long id, @Valid @RequestBody UpdatePetRequestDTO request) {
        return new ResponseEntity<>(petService.updatePet(id, request), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> get(@PathVariable("id") Long id) {
        return new ResponseEntity<>(petService.getPetById(id), HttpStatus.OK);
    }*/

}
