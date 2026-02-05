package com.example.vetconnect.users.services;

import com.example.vetconnect.Utils.Utils;
import com.example.vetconnect.authentication.JWT.JwtService;
import com.example.vetconnect.authentication.JWT.JwtUserPrincipal;
import com.example.vetconnect.clinics.Repository.ClinicRepository;
import com.example.vetconnect.clinics.dto.ClinicSummaryDTO;
import com.example.vetconnect.clinics.enitity.Clinic;
import com.example.vetconnect.users.Repository.UserRepository;
import com.example.vetconnect.users.dto.CreateUserRequest;
import com.example.vetconnect.users.dto.UpdateUserRequest;
import com.example.vetconnect.users.dto.UserResponse;
import com.example.vetconnect.users.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImplement implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;
    private final JwtService jwtService;
    private final Utils utils;


    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return users.stream().map(user -> {
            Clinic clinic = user.getClinic();
            ClinicSummaryDTO clinicResponse = clinic == null ? null
                    : new ClinicSummaryDTO(
                    clinic.getId(),
                    clinic.getName(),
                    clinic.getAddress(),
                    clinic.getPhone(),
                    clinic.getCreatedAt(),
                    clinic.getUpdatedAt()
            );
                    return new UserResponse(
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            user.getRole(),
                            clinicResponse, // ✅ safe
                            user.getCreatedAt()
                    );
                })
                .toList();
        }

    @Override
    public String createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        User user = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        userRepository.save(user);
        return "User created successfully";
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        /* System.out.println(jwtService.getUserDataFromToken().getId());*/
        JwtUserPrincipal userDataFromToken = jwtService.getUserDataFromToken();
        /*System.out.println(userDataFromToken.getId());
        System.out.println(userDataFromToken.getUserName());
        System.out.println(userDataFromToken.getEmail());
        System.out.println(userDataFromToken.getRole());*/
        if (!jwtService.isUserAdminOrOwner(id)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to update this user"
            );
        }
      /*   if (request.getName() != null && !request.getName().isEmpty()) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            user.setEmail(request.getEmail());
        }
        if (!(request.getRole() == null)) {
            user.setRole(request.getRole());
        }*/
        utils.updateIfPresent(request.getName(), user::setName);
        utils.updateIfPresent(request.getEmail(), user::setEmail);
        if (userDataFromToken.getRole().name().equals("admin")) {
            /*utils.updateIfPresent(request.getRole(), user::setRole);*/
            if (request.getRole() != null) {
                System.out.println(request.getRole());
                user.setRole(request.getRole());
            }
            if (request.getClinic_id() != null && (user.getRole().name().equals("vet") || request.getRole().name().equals("vet"))) {
                Clinic clinic = clinicRepository.findById(request.getClinic_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found"));
                user.setClinic(clinic);
            }
        }

        userRepository.save(user);
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                new ClinicSummaryDTO(
                        user.getClinic().getId(),
                        user.getClinic().getName(),
                        user.getClinic().getAddress(),
                        user.getClinic().getPhone(),
                        user.getClinic().getCreatedAt(),
                        user.getClinic().getUpdatedAt()
                ),
                user.getCreatedAt()
        );
    }

    @Override
    public String deleteUser(Long id) {
        System.out.println(id);
        if (!jwtService.isUserAdminOrOwner(id)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to delete this user"
            );
        }
        if (!userRepository.existsById(id)) {
            return "User Doesnt exist";
        }
        userRepository.deleteById(id);
        return "User deleted successfully";
    }
}
