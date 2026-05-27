package za.gov.dpw.iarts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import za.gov.dpw.iarts.dto.AuthRequest;
import za.gov.dpw.iarts.dto.AuthResponse;
import za.gov.dpw.iarts.dto.CreateUserRequest;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.service.AuthService;
import za.gov.dpw.iarts.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }
}
