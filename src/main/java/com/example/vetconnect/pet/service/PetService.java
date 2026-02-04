package com.example.vetconnect.pet.service;

import com.example.vetconnect.pet.dto.CreatePetRequest;
import com.example.vetconnect.pet.dto.PetResponse;
import com.example.vetconnect.pet.dto.UpdatePetRequestDTO;

import java.util.List;

public interface PetService {
    String addPet(CreatePetRequest request);
    String deletePet(Long id);
    List<PetResponse> getAllPets();
    String updatePet(Long id, UpdatePetRequestDTO request);
    PetResponse getPetById(Long id);
}
