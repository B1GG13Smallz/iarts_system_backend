package za.gov.dpw.iarts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.gov.dpw.iarts.dto.RegisterEntryDto;
import za.gov.dpw.iarts.entity.RegisterEntry;
import za.gov.dpw.iarts.security.UserPrincipal;
import za.gov.dpw.iarts.service.RegisterEntryService;
import java.util.Map;

@RestController
@RequestMapping("/api/registers")
@RequiredArgsConstructor
public class RegisterEntryController {
    private final RegisterEntryService registerEntryService;

    @PostMapping
    public Map<String, Object> create(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody RegisterEntryDto dto) {
        RegisterEntry entry = registerEntryService.create(principal.user(), dto);
        return Map.of(
                "id", entry.getId(),
                "message", "Register saved"
        );
    }
}
