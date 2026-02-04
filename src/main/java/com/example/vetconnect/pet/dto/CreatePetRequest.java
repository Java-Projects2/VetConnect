package com.example.vetconnect.pet.dto;

import com.example.vetconnect.users.entity.User;
import jakarta.validation.constraints.*;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.vetconnect.pet.entity.Pet}
 */
@Value
public class CreatePetRequest implements Serializable {

    @NotNull(message = "Name Required")
    @Size(max = 255)
    @NotEmpty(message = "Name Required")
    @NotBlank(message = "Name Required")
    String name;
    @NotNull(message = "Type Required")
    @Size(max = 255)
    @NotEmpty(message = "Type Required")
    @NotBlank(message = "Type Required")
    String type;
    @NotNull(message = "Breed Required")
    @Size(max = 255)
    @NotEmpty(message = "Breed Required")
    @NotBlank(message = "Breed Required")
    String breed;
    @NotNull(message = "Age Required")
    @Min(message = "Age 0-100", value = 0)
    @Max(message = "Age 0-100", value = 100)
    Integer age;
}