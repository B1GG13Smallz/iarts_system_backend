package za.gov.dpw.iarts.dto;

import java.util.Set;
import java.util.List;

public record AuthResponse(String token, String tokenType, Long userId, String username, Set<String> roles, List<String> routes) {}
