package za.gov.dpw.iarts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.gov.dpw.iarts.dto.PermissionRemovalDto;
import za.gov.dpw.iarts.entity.PermissionRemoval;
import za.gov.dpw.iarts.security.UserPrincipal;
import za.gov.dpw.iarts.service.PermissionRemovalPdfService;
import za.gov.dpw.iarts.service.PermissionRemovalService;
import java.util.Map;

@RestController
@RequestMapping("/api/permission-removals")
@RequiredArgsConstructor
public class PermissionRemovalController {
    private final PermissionRemovalService permissionRemovalService;
    private final PermissionRemovalPdfService permissionRemovalPdfService;

    @PostMapping
    public Map<String, Object> create(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody PermissionRemovalDto dto) {
        PermissionRemoval removal = permissionRemovalService.create(principal.user(), dto);
        return Map.of(
                "id", removal.getId(),
                "message", "Permission to remove equipment saved"
        );
    }

    @PostMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(@Valid @RequestBody PermissionRemovalDto dto) {
        byte[] pdf = permissionRemovalPdfService.generate(dto);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.inline()
                        .filename("permission-to-remove-equipment.pdf")
                        .build()
                        .toString())
                .body(pdf);
    }
}
