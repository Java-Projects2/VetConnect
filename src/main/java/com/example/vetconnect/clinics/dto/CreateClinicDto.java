package com.example.vetconnect.clinics.dto;

import com.example.vetconnect.users.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.example.vetconnect.clinics.enitity.Clinic}
 */
@Getter
@Setter
public class CreateClinicDto implements Serializable {
    @NotNull
    @Size(max = 255)
    @NotBlank(message = "name of clinic is required")
    @Length(min = 3, max = 100)
    String name;
    @NotNull
    @Size(max = 255)
    @NotBlank(message = "address of clinic is required")
    String address;
    @NotNull
    @Size(max = 11)
    String phone;

    private List<Long> vetIds;

}