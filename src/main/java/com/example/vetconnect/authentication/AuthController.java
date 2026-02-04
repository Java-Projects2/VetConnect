package com.example.vetconnect.authentication;

import com.example.vetconnect.authentication.JWT.JwtService;
import com.example.vetconnect.authentication.dto.LoginRequest;
import com.example.vetconnect.users.Repository.UserRepository;
import com.example.vetconnect.users.dto.CreateUserRequest;
import com.example.vetconnect.users.entity.User;
import com.example.vetconnect.users.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;
    private JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(

            @Valid
            @RequestBody
            CreateUserRequest request) {
        String response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String token = jwtService.generateToken(user.getId(), user.getName(), request.getEmail(), user.getRole());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "message", "Login successful"
        ));
    }

}
