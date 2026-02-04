package com.example.vetconnect.clinics.controller;

import com.example.vetconnect.clinics.dto.CreateClinicDto;
import com.example.vetconnect.clinics.dto.ResponseClinicDto;
import com.example.vetconnect.clinics.dto.UpdateClinicDto;
import com.example.vetconnect.clinics.enitity.Clinic;
import com.example.vetconnect.clinics.service.ClinicService;
import com.example.vetconnect.users.Repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clinics")
public class ClinicController {
    private final ClinicService clinicService;
    private final UserRepository userRepository;

    public ClinicController(ClinicService clinicService, UserRepository userRepository) {
        this.clinicService = clinicService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public String createClinic(@RequestBody CreateClinicDto request) {
        Clinic clinic = clinicService.createClinic(request);
        return "clinic created successfully";
    }

    /*@GetMapping
    public String getAll(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        Page<ResponseClinicDto> clinicsPage = clinicService.getAll(pageable);
        model.addAttribute("clinicsPage", clinicsPage);
        return "clinics";
    }

    @PatchMapping("{id}")
    public String updateClinic(@PathVariable("id") Long id, @Valid @ModelAttribute("clinicDto") UpdateClinicDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("vets", userRepository.findAll());
            return "clinics";
        }
        Clinic updateClinic = clinicService.updateClinic(dto, id);
        model.addAttribute("clinic", updateClinic);
        return "clinics/success";
    }*/

}
