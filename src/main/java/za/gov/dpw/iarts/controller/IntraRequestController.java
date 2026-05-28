package za.gov.dpw.iarts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.gov.dpw.iarts.dto.IntraRequestDto;
import za.gov.dpw.iarts.dto.IntraRequestStatusDto;
import za.gov.dpw.iarts.dto.TechnicianRequestDetailsDto;
import za.gov.dpw.iarts.entity.IntraRequest;
import za.gov.dpw.iarts.security.UserPrincipal;
import za.gov.dpw.iarts.service.IntraRequestService;
import java.util.List;

@RestController
@RequestMapping("/api/intra-requests")
@RequiredArgsConstructor
public class IntraRequestController {
    private final IntraRequestService intraRequestService;

    @PostMapping
    public IntraRequest create(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody IntraRequestDto dto) {
        return intraRequestService.create(principal.user(), dto);
    }

    @GetMapping
    public List<IntraRequest> all() {
        return intraRequestService.findAll();
    }

    @GetMapping("/mine")
    public List<IntraRequest> mine(@AuthenticationPrincipal UserPrincipal principal) {
        return intraRequestService.findMine(principal.user());
    }

    @GetMapping("/reference/{referenceNumber}")
    public TechnicianRequestDetailsDto findByReference(@PathVariable String referenceNumber) {
        return intraRequestService.findTechnicianDetailsByReference(referenceNumber);
    }

    @PatchMapping("/{id}/status")
    public IntraRequest updateStatus(@PathVariable Long id, @Valid @RequestBody IntraRequestStatusDto dto) {
        return intraRequestService.updateStatus(id, dto);
    }
}
