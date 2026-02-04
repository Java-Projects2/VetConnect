package com.example.vetconnect.pet.controller;

import com.example.vetconnect.pet.dto.CreatePetRequest;
import com.example.vetconnect.pet.dto.PetResponse;
import com.example.vetconnect.pet.dto.UpdatePetRequestDTO;
import com.example.vetconnect.pet.service.PetService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet")
@AllArgsConstructor
public class PetController {
    private final PetService petService;


    @PostMapping
    public ResponseEntity<String> addPet(@Valid @RequestBody CreatePetRequest pet) {
        return new ResponseEntity<>(petService.addPet(pet), HttpStatus.CREATED);
    }

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
    }

}
