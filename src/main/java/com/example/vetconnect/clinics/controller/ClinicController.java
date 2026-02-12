package com.example.vetconnect.clinics.controller;

import com.example.vetconnect.clinics.dto.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<ClinicResponseDTO> getSingleClinic(@PathVariable("id") Long id) {
        ClinicResponseDTO clinic = clinicService.getSingleClinic(id);
        return ResponseEntity.ok(clinic);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateBasicInfoClinic(@RequestBody UpdateClinicDto request, @PathVariable("id") Long id) {
        clinicService.updateClinic(request, id);
        return ResponseEntity.ok("clinic updated successfully");
    }

    @PutMapping("/{id}/vets/add")
    public ResponseEntity<String> addVetToClinic(@PathVariable("id") Long id, @RequestBody UpdateVetsDto request) {
        clinicService.addVetsToClinic(id, request);
        return ResponseEntity.ok("vet added successfully");
    }

    @PutMapping("/{id}/vets/remove")
    public ResponseEntity<String> removeVetsFromClinic(@PathVariable("id") Long id, @RequestBody UpdateVetsDto request) {

        clinicService.removeVetsFromClinic(id, request);
        return ResponseEntity.ok("vet removed successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClinic(@PathVariable("id") Long id) {
        clinicService.deleteClinic(id);
        return ResponseEntity.ok("Clinic removed successfully");
    }
}
