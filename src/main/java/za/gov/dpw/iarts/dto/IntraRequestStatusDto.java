package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;

public record IntraRequestStatusDto(@NotBlank String status) {}
