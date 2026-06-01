package za.gov.dpw.iarts.dto;

import java.time.LocalDateTime;

public record AvailabilityRequestResponseDto(
        Long id,
        String requesterName,
        String referenceNumber,
        String equipment,
        String description,
        String serialNumber,
        String barCodeNumber,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
