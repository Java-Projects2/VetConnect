package com.example.vetconnect.users.services;

import com.example.vetconnect.Utils.Utils;
import com.example.vetconnect.authentication.JWT.JwtService;
import com.example.vetconnect.authentication.JWT.JwtUserPrincipal;
import com.example.vetconnect.clinics.Repository.ClinicRepository;
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
import java.util.stream.Collectors;

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
        return users.stream().map(user -> new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getClinic()
        )).collect(Collectors.toList());
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
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        /* System.out.println(jwtService.getUserDataFromToken().getId());*/
        JwtUserPrincipal userDataFromToken = jwtService.getUserDataFromToken();
        System.out.println(userDataFromToken.getId());
        System.out.println(userDataFromToken.getUserName());
        System.out.println(userDataFromToken.getEmail());
        System.out.println(userDataFromToken.getRole());
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
            utils.updateIfPresent(request.getRole(), user::setRole);
            if (request.getClinic_id() != null) {
               Clinic clinic = clinicRepository.findById(request.getClinic_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clinic not found"));
               user.setClinic(clinic);
            }
        }


        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getClinic()
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
