package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.AssetApprovalDto;
import za.gov.dpw.iarts.entity.AssetApproval;
import za.gov.dpw.iarts.entity.IntraRequest;
import za.gov.dpw.iarts.entity.PermissionRemoval;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.exception.ResourceNotFoundException;
import za.gov.dpw.iarts.repository.AssetApprovalRepository;
import za.gov.dpw.iarts.repository.IntraRequestRepository;
import za.gov.dpw.iarts.repository.PermissionRemovalRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AssetApprovalService {
    private final AssetApprovalRepository assetApprovalRepository;
    private final IntraRequestRepository intraRequestRepository;
    private final PermissionRemovalRepository permissionRemovalRepository;
    private final PermissionRemovalService permissionRemovalService;

    public AssetApproval create(User approvedBy, AssetApprovalDto dto) {
        if (dto.requestId() == null && dto.permissionRemovalId() == null) {
            throw new IllegalArgumentException("INTRA request or permission removal form is required");
        }

        AssetApproval approval = new AssetApproval();
        if (dto.requestId() != null) {
            IntraRequest request = intraRequestRepository.findById(dto.requestId())
                    .orElseThrow(() -> new ResourceNotFoundException("INTRA request not found"));
            approval.setRequest(request);
        }
        if (dto.permissionRemovalId() != null) {
            PermissionRemoval permissionRemoval = permissionRemovalRepository.findById(dto.permissionRemovalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Permission removal form not found"));
            approval.setPermissionRemoval(permissionRemoval);
        }
        approval.setApprovedBy(approvedBy);
        approval.setMovableAssetName(dto.movableAssetName());
        approval.setApprovalDate(parseApprovalDate(dto.approvalDate()));
        approval.setSignatureFileName(dto.signatureFileName());
        approval.setSignatureContentType(dto.signatureContentType());
        approval.setSignatureData(decodeSignature(dto.signatureBase64()));
        AssetApproval savedApproval = assetApprovalRepository.save(approval);
        if (dto.permissionRemovalId() != null) {
            permissionRemovalService.markAssetsApproved(dto.permissionRemovalId());
        }
        return savedApproval;
    }

    private LocalDateTime parseApprovalDate(String approvalDate) {
        try {
            return LocalDate.parse(approvalDate).atStartOfDay();
        } catch (DateTimeParseException ex) {
            try {
                return LocalDateTime.parse(approvalDate);
            } catch (DateTimeParseException nestedEx) {
                throw new IllegalArgumentException("Approval date is invalid");
            }
        }
    }

    private byte[] decodeSignature(String signatureBase64) {
        try {
            return Base64.getDecoder().decode(signatureBase64);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Signature file is not valid base64");
        }
    }
}
