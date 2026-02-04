package com.example.vetconnect.clinics.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClinicDto {

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String address;

    @Size(max = 11)
    private String phone;

    private Long vetId;

}
