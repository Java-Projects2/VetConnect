package com.example.vetconnect.pet.dto;

import lombok.Getter;
import lombok.Value;

import java.io.Serializable;

@Getter
public class UpdatePetRequestDTO implements Serializable {
    String name;
    String type;
    String breed;
    Integer age;
}