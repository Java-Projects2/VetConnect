package com.example.vetconnect.clinics.service;

import com.example.vetconnect.clinics.dto.ClinicResponseDTO;
import com.example.vetconnect.clinics.dto.CreateClinicDto;
import com.example.vetconnect.clinics.dto.UpdateClinicDto;
import com.example.vetconnect.clinics.dto.UpdateVetsDto;
import com.example.vetconnect.clinics.enitity.Clinic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface ClinicService {
    public Clinic createClinic(CreateClinicDto request);

    public Page<ClinicResponseDTO> getAllClinics(Pageable pageable);

    public ClinicResponseDTO getSingleClinic(Long id);

    public void updateClinic(UpdateClinicDto request, Long id);

    public void deleteClinic(Long id);

    public void addVetsToClinic(Long id , UpdateVetsDto  request);

    public void removeVetsFromClinic(Long id , UpdateVetsDto  request);
}
