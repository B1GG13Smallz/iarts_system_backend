package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.AssetApprovalDto;
import za.gov.dpw.iarts.entity.AssetApproval;
import za.gov.dpw.iarts.entity.IntraRequest;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.exception.ResourceNotFoundException;
import za.gov.dpw.iarts.repository.AssetApprovalRepository;
import za.gov.dpw.iarts.repository.IntraRequestRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AssetApprovalService {
    private final AssetApprovalRepository assetApprovalRepository;
    private final IntraRequestRepository intraRequestRepository;

    public AssetApproval create(User approvedBy, AssetApprovalDto dto) {
        IntraRequest request = intraRequestRepository.findById(dto.requestId())
                .orElseThrow(() -> new ResourceNotFoundException("INTRA request not found"));

        AssetApproval approval = new AssetApproval();
        approval.setRequest(request);
        approval.setApprovedBy(approvedBy);
        approval.setMovableAssetName(dto.movableAssetName());
        approval.setApprovalDate(parseApprovalDate(dto.approvalDate()));
        approval.setSignatureFileName(dto.signatureFileName());
        approval.setSignatureContentType(dto.signatureContentType());
        approval.setSignatureData(decodeSignature(dto.signatureBase64()));
        return assetApprovalRepository.save(approval);
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
