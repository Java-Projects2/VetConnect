package com.example.vetconnect.authentication.JWT;

import com.example.vetconnect.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtUserPrincipal {
    private Long id;
    private String email;
    private String userName;
    private User.Role role;
}
