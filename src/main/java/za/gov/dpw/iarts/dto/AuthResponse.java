package za.gov.dpw.iarts.dto;

import java.util.Set;

public record AuthResponse(String token, String tokenType, Long userId, String username, Set<String> roles) {}
