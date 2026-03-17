package com.example.vetconnect.appointment.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VetDto {
    private Long id;
    private String name;

    public VetDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

