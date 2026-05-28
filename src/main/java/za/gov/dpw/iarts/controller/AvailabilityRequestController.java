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
    public AvailabilityRequest create(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody AvailabilityRequestDto dto) {
        return availabilityRequestService.create(principal.user(), dto);
    }

    @GetMapping("/mine/latest")
    public AvailabilityRequest latestMine(@AuthenticationPrincipal UserPrincipal principal) {
        return availabilityRequestService.latestFor(principal.user());
    }

    @GetMapping
    public List<AvailabilityRequest> all() {
        return availabilityRequestService.findAll();
    }

    @PatchMapping("/{id}/status")
    public AvailabilityRequest updateStatus(@PathVariable Long id, @Valid @RequestBody AvailabilityStatusDto dto) {
        return availabilityRequestService.updateStatus(id, dto);
    }
}
