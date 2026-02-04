package com.example.vetconnect.pet.service;

import com.example.vetconnect.Utils.Utils;
import com.example.vetconnect.authentication.JWT.JwtService;
import com.example.vetconnect.authentication.JWT.JwtUserPrincipal;
import com.example.vetconnect.pet.dto.CreatePetRequest;
import com.example.vetconnect.pet.dto.PetResponse;
import com.example.vetconnect.pet.dto.UpdatePetRequestDTO;
import com.example.vetconnect.pet.entity.Pet;
import com.example.vetconnect.pet.repository.PetRepository;
import com.example.vetconnect.users.dto.UserResponse;
import com.example.vetconnect.users.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PetServiceImplement implements PetService {
    private JwtService jwtService;
    private final Utils utils;
    @PersistenceContext
    private EntityManager entityManager;
    private PetRepository petRepository;

    @Override
    public String addPet(CreatePetRequest request) {
        JwtUserPrincipal userDataFromToken = jwtService.getUserDataFromToken();
        User user = entityManager.getReference(User.class, userDataFromToken.getId());
        Pet pet = new Pet(
                request.getName(),
                request.getType(),
                request.getBreed(),
                request.getAge(),
                user
        );
        petRepository.save(pet);
        return "Pet Created Successfully";
    }

    @Override
    public List<PetResponse> getAllPets() {
        List<Pet> pets = petRepository.findAll();
        return pets.stream()
                .map(PetResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public String deletePet(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet does not exist"));
        if (!jwtService.isUserAdminOrOwner(pet.getUser().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to delete this pet"
            );
        }
        petRepository.delete(pet);
        return "Pet Deleted Successfully";
    }


    @Override
    public String updatePet(Long id, UpdatePetRequestDTO request) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet does not exist"));
        if (!jwtService.isUserAdminOrOwner(pet.getUser().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to update this pet"
            );
        }

    /*    if (request.getName() != null && !request.getName().isEmpty()) {
            pet.setName(request.getName());
        }
        if ((request.getType() != null && !request.getType().isEmpty())) {
            pet.setType(request.getType());
        }
        if ((request.getBreed() != null && !request.getBreed().isEmpty())) {
            pet.setBreed(request.getBreed());
        }
        if (request.getAge() != null) {
            pet.setAge(request.getAge());
        }*/
        utils.updateIfPresent(request.getName(), pet::setName);
        utils.updateIfPresent(request.getType(), pet::setType);
        utils.updateIfPresent(request.getBreed(), pet::setBreed);

        if (request.getAge() != null) {
            pet.setAge(request.getAge());
        }

        petRepository.save(pet);
        return "Pet Updated Successfully";
    }

    @Override
    public PetResponse getPetById(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet does not exist"));
        return new PetResponse(pet);
    }
}
