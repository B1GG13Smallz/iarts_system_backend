package za.gov.dpw.iarts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.gov.dpw.iarts.dto.PermissionRemovalDto;
import za.gov.dpw.iarts.dto.PermissionRemovalRecordDto;
import za.gov.dpw.iarts.entity.PermissionRemoval;
import za.gov.dpw.iarts.security.UserPrincipal;
import za.gov.dpw.iarts.service.PermissionRemovalPdfService;
import za.gov.dpw.iarts.service.PermissionRemovalService;
import java.util.List;
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

    @PostMapping("/send-to-storeroom")
    public Map<String, Object> sendToStoreroom(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody PermissionRemovalDto dto) {
        PermissionRemoval removal = permissionRemovalService.sendToStoreroom(principal.user(), dto);
        return Map.of(
                "id", removal.getId(),
                "message", "Permission to remove equipment sent to storeroom"
        );
    }

    @GetMapping
    public List<PermissionRemovalRecordDto> search(
            @RequestParam(required = false) String identityOrPersalNumber,
            @RequestParam(required = false) String workflowStatus
    ) {
        return permissionRemovalService.search(identityOrPersalNumber, workflowStatus);
    }

    @GetMapping("/{id}")
    public PermissionRemovalRecordDto get(@PathVariable Long id) {
        return permissionRemovalService.getRecord(id);
    }

    @PostMapping("/{id}/send-to-assets")
    public Map<String, Object> signIctAndSendToAssets(@PathVariable Long id, @Valid @RequestBody PermissionRemovalDto dto) {
        PermissionRemoval removal = permissionRemovalService.signIctAndSendToAssets(id, dto);
        return Map.of(
                "id", removal.getId(),
                "message", "Permission to remove equipment sent to assets"
        );
    }

    @PostMapping("/{id}/assets-approval")
    public Map<String, Object> saveAssetsApproval(@PathVariable Long id, @Valid @RequestBody PermissionRemovalDto dto) {
        PermissionRemoval removal = permissionRemovalService.saveAssetsApproval(id, dto);
        return Map.of(
                "id", removal.getId(),
                "message", "Permission to remove equipment approved by assets"
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
