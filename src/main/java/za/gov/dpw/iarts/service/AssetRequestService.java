package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.ApprovalRequestDto;
import za.gov.dpw.iarts.dto.AssetRequestDto;
import za.gov.dpw.iarts.exception.ResourceNotFoundException;
import za.gov.dpw.iarts.entity.Approval;
import za.gov.dpw.iarts.entity.AssetRequest;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.constants.AssetTypes;
import za.gov.dpw.iarts.constants.AuditActions;
import za.gov.dpw.iarts.constants.RequestStatuses;
import za.gov.dpw.iarts.repository.ApprovalRepository;
import za.gov.dpw.iarts.repository.AssetRequestRepository;
import za.gov.dpw.iarts.repository.DepartmentRepository;
import za.gov.dpw.iarts.repository.UserRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetRequestService {
    private final AssetRequestRepository requestRepository;
    private final ApprovalRepository approvalRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final AuditService auditService;

    public AssetRequest create(AssetRequestDto dto) {
        User requester = userRepository.findById(dto.requesterId()).orElseThrow(() -> new ResourceNotFoundException("Requester not found"));
        AssetRequest request = new AssetRequest();
        request.setRequester(requester);
        if (dto.departmentId() != null) request.setDepartment(departmentRepository.findById(dto.departmentId()).orElseThrow(() -> new ResourceNotFoundException("Department not found")));
        request.setAssetType(dto.assetType());
        request.setJustification(dto.justification());
        if (AssetTypes.PRINTER.equals(dto.assetType())) {
            request.setPrinterRoutedToQts(true);
            request.setStatus(RequestStatuses.QTS_ROUTED);
            request.setQtsReference(dto.qtsReference());
        }
        AssetRequest saved = requestRepository.save(request);
        auditService.record(requester, AuditActions.REQUEST_CREATED, "AssetRequest", saved.getId(), "Asset request submitted");
        return saved;
    }

    public AssetRequest get(Long id) {
        return requestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Request not found"));
    }

    public List<AssetRequest> findAll() {
        return requestRepository.findAll();
    }

    public AssetRequest approve(Long id, ApprovalRequestDto dto) {
        AssetRequest request = get(id);
        User approver = userRepository.findById(dto.approverId()).orElseThrow(() -> new ResourceNotFoundException("Approver not found"));
        Approval approval = new Approval();
        approval.setRequest(request);
        approval.setApprover(approver);
        approval.setDecision(dto.decision());
        approval.setComments(dto.comments());
        approvalRepository.save(approval);
        request.setStatus("APPROVED".equals(dto.decision()) ? RequestStatuses.APPROVED : RequestStatuses.REJECTED);
        AssetRequest saved = requestRepository.save(request);
        auditService.record(approver, RequestStatuses.APPROVED.equals(saved.getStatus()) ? AuditActions.REQUEST_APPROVED : AuditActions.REQUEST_REJECTED, "AssetRequest", saved.getId(), dto.comments());
        return saved;
    }
}
