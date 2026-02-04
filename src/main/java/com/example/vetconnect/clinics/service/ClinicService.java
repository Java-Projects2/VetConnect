package com.example.vetconnect.clinics.service;

import com.example.vetconnect.clinics.dto.CreateClinicDto;
import com.example.vetconnect.clinics.dto.ResponseClinicDto;
import com.example.vetconnect.clinics.dto.UpdateClinicDto;
import com.example.vetconnect.clinics.enitity.Clinic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ClinicService {
    public Clinic createClinic(CreateClinicDto dto);

    public Page<ResponseClinicDto> getAll(Pageable pageable);

    public Clinic updateClinic(UpdateClinicDto dto,Long id);

}
