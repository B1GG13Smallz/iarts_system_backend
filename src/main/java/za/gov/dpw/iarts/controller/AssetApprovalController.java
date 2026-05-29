package za.gov.dpw.iarts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.gov.dpw.iarts.dto.AssetApprovalDto;
import za.gov.dpw.iarts.entity.AssetApproval;
import za.gov.dpw.iarts.security.UserPrincipal;
import za.gov.dpw.iarts.service.AssetApprovalService;
import java.util.Map;

@RestController
@RequestMapping("/api/asset-approvals")
@RequiredArgsConstructor
public class AssetApprovalController {
    private final AssetApprovalService assetApprovalService;

    @PostMapping
    public Map<String, Object> create(@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody AssetApprovalDto dto) {
        AssetApproval approval = assetApprovalService.create(principal.user(), dto);
        return Map.of(
                "id", approval.getId(),
                "message", "Asset approval saved"
        );
    }
}
