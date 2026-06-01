package za.gov.dpw.iarts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.gov.dpw.iarts.dto.AvailabilityRequestDto;
import za.gov.dpw.iarts.dto.AvailabilityRequestResponseDto;
import za.gov.dpw.iarts.dto.AvailabilityStatusDto;
import za.gov.dpw.iarts.entity.AvailabilityRequest;
import za.gov.dpw.iarts.security.UserPrincipal;
import za.gov.dpw.iarts.service.AvailabilityRequestService;
import java.util.List;

@RestController
@RequestMapping("/api/availability-requests")
@RequiredArgsConstructor
public class AvailabilityRequestController {
    private final AvailabilityRequestService availabilityRequestService;

    @PostMapping
    public AvailabilityRequestResponseDto create(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody AvailabilityRequestDto dto) {
        return toDto(availabilityRequestService.create(principal.user(), dto));
    }

    @GetMapping("/mine/latest")
    public AvailabilityRequestResponseDto latestMine(@AuthenticationPrincipal UserPrincipal principal) {
        return toDto(availabilityRequestService.latestFor(principal.user()));
    }

    @GetMapping
    public List<AvailabilityRequestResponseDto> all() {
        return availabilityRequestService.findAll().stream().map(this::toDto).toList();
    }

    @PatchMapping("/{id}/status")
    public AvailabilityRequestResponseDto updateStatus(@PathVariable Long id, @Valid @RequestBody AvailabilityStatusDto dto) {
        return toDto(availabilityRequestService.updateStatus(id, dto));
    }

    private AvailabilityRequestResponseDto toDto(AvailabilityRequest request) {
        return new AvailabilityRequestResponseDto(
                request.getId(),
                requesterName(request),
                request.getReferenceNumber(),
                request.getEquipment(),
                request.getDescription(),
                request.getSerialNumber(),
                request.getBarCodeNumber(),
                request.getStatus(),
                request.getCreatedAt(),
                request.getUpdatedAt()
        );
    }

    private String requesterName(AvailabilityRequest request) {
        if (request.getRequesterName() != null && !request.getRequesterName().isBlank()) {
            return request.getRequesterName();
        }
        if (request.getRequester().getFullName() != null && !request.getRequester().getFullName().isBlank()) {
            return request.getRequester().getFullName();
        }
        return request.getRequester().getUsername();
    }
}
