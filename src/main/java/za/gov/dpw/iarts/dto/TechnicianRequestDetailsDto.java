package za.gov.dpw.iarts.dto;

import za.gov.dpw.iarts.entity.IntraRequest;

public record TechnicianRequestDetailsDto(IntraRequest request, String availabilityStatus, String equipment) {}
