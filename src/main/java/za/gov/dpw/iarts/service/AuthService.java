package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.AuthRequest;
import za.gov.dpw.iarts.dto.AuthResponse;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.constants.AuditActions;
import za.gov.dpw.iarts.repository.UserRepository;
import za.gov.dpw.iarts.security.CustomUserDetailsService;
import za.gov.dpw.iarts.security.JwtService;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuditService auditService;

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        var details = userDetailsService.loadUserByUsername(request.username());
        String token = jwtService.generateToken(details);
        User user = userRepository.findByUsername(request.username()).orElseThrow();
        auditService.record(user, AuditActions.LOGIN, "User", user.getId(), "Login successful");
        return new AuthResponse(token, "Bearer", user.getId(), user.getUsername(), user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
    }
}
