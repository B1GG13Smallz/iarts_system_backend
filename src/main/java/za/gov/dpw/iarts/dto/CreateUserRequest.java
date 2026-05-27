package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record CreateUserRequest(@NotBlank String username, @Email String email, @NotBlank String password, @NotBlank String fullName, String employeeNumber, Long departmentId, Set<String> roles) {}
