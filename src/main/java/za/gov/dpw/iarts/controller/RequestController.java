package za.gov.dpw.iarts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.gov.dpw.iarts.dto.ApprovalRequestDto;
import za.gov.dpw.iarts.dto.AssetRequestDto;
import za.gov.dpw.iarts.entity.AssetRequest;
import za.gov.dpw.iarts.service.AssetRequestService;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {
    private final AssetRequestService requestService;

    @PostMapping
    public AssetRequest create(@Valid @RequestBody AssetRequestDto dto) {
        return requestService.create(dto);
    }

    @GetMapping
    public List<AssetRequest> all() {
        return requestService.findAll();
    }

    @GetMapping("/{id}")
    public AssetRequest get(@PathVariable Long id) {
        return requestService.get(id);
    }

    @PostMapping("/{id}/approve")
    public AssetRequest approve(@PathVariable Long id, @Valid @RequestBody ApprovalRequestDto dto) {
        return requestService.approve(id, dto);
    }
}
