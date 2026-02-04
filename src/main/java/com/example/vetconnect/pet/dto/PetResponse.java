package com.example.vetconnect.pet.dto;

import com.example.vetconnect.pet.entity.Pet;
import com.example.vetconnect.users.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.vetconnect.pet.entity.Pet}
 */
@Value
@AllArgsConstructor
public class PetResponse implements Serializable {
    Long id;
    @NotNull
    @Size(max = 255)
    String name;
    @NotNull
    @Size(max = 255)
    String type;
    @NotNull
    @Size(max = 255)
    String breed;
    @NotNull
    Integer age;
    @NotNull
    User user;


    public PetResponse(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.type = pet.getType();
        this.breed = pet.getBreed();
        this.age = pet.getAge();
        this.user = pet.getUser();
    }
}