package com.example.vetconnect.clinics.controller;

import com.example.vetconnect.clinics.dto.CreateClinicDto;
import com.example.vetconnect.clinics.enitity.Clinic;
import com.example.vetconnect.clinics.service.ClinicService;
import com.example.vetconnect.users.Repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/clinics")
@AllArgsConstructor
public class ClinicController {
    private final ClinicService clinicService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Clinic> addPet(@Valid @RequestBody CreateClinicDto request) {
        return new ResponseEntity<>(clinicService.createClinic(request), HttpStatus.CREATED);
    }
/*
    @GetMapping
    public ResponseEntity<List<PetResponse>> getAllPets() {
        List<PetResponse> petResponse = petService.getAllPets();
        return new ResponseEntity<>(petResponse, HttpStatus.OK);
    }

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
