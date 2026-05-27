package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.AssignmentDto;
import za.gov.dpw.iarts.dto.EquipmentDto;
import za.gov.dpw.iarts.dto.PolicyAcceptanceDto;
import za.gov.dpw.iarts.dto.StockSummaryDto;
import za.gov.dpw.iarts.exception.ResourceNotFoundException;
import za.gov.dpw.iarts.entity.Assignment;
import za.gov.dpw.iarts.entity.Equipment;
import za.gov.dpw.iarts.entity.PolicyAcceptance;
import za.gov.dpw.iarts.entity.StockRecord;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.constants.AuditActions;
import za.gov.dpw.iarts.constants.StockStatuses;
import za.gov.dpw.iarts.repository.AssetRequestRepository;
import za.gov.dpw.iarts.repository.AssignmentRepository;
import za.gov.dpw.iarts.repository.EquipmentRepository;
import za.gov.dpw.iarts.repository.PolicyAcceptanceRepository;
import za.gov.dpw.iarts.repository.StockRecordRepository;
import za.gov.dpw.iarts.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final EquipmentRepository equipmentRepository;
    private final StockRecordRepository stockRecordRepository;
    private final AssignmentRepository assignmentRepository;
    private final PolicyAcceptanceRepository policyAcceptanceRepository;
    private final UserRepository userRepository;
    private final AssetRequestRepository requestRepository;
    private final AuditService auditService;

    public Equipment createEquipment(EquipmentDto dto) {
        Equipment equipment = new Equipment();
        equipment.setAssetTag(dto.assetTag());
        equipment.setSerialNumber(dto.serialNumber());
        equipment.setAssetType(dto.assetType());
        equipment.setMake(dto.make());
        equipment.setModel(dto.model());
        equipment.setLocation(dto.location());
        equipment.setNetTrackReference(dto.netTrackReference());
        equipment.setLaptopPolicyRequired(dto.laptopPolicyRequired());
        Equipment saved = equipmentRepository.save(equipment);
        StockRecord stock = new StockRecord();
        stock.setEquipment(saved);
        stock.setStatus(StockStatuses.AVAILABLE);
        stockRecordRepository.save(stock);
        return saved;
    }

    public Assignment assign(AssignmentDto dto) {
        Equipment equipment = equipmentRepository.findById(dto.equipmentId()).orElseThrow(() -> new ResourceNotFoundException("Equipment not found"));
        User assignedTo = userRepository.findById(dto.assignedToId()).orElseThrow(() -> new ResourceNotFoundException("Assigned user not found"));
        User issuedBy = userRepository.findById(dto.issuedById()).orElseThrow(() -> new ResourceNotFoundException("Issuer not found"));
        Assignment assignment = new Assignment();
        assignment.setEquipment(equipment);
        assignment.setAssignedTo(assignedTo);
        assignment.setIssuedBy(issuedBy);
        assignment.setIssuedDate(LocalDate.now());
        if (dto.requestId() != null) assignment.setRequest(requestRepository.findById(dto.requestId()).orElseThrow(() -> new ResourceNotFoundException("Request not found")));
        Assignment saved = assignmentRepository.save(assignment);
        auditService.record(issuedBy, AuditActions.ASSET_ASSIGNED, "Assignment", saved.getId(), "Asset assigned to " + assignedTo.getUsername());
        return saved;
    }

    public PolicyAcceptance acceptPolicy(PolicyAcceptanceDto dto) {
        PolicyAcceptance acceptance = new PolicyAcceptance();
        acceptance.setUser(userRepository.findById(dto.userId()).orElseThrow(() -> new ResourceNotFoundException("User not found")));
        acceptance.setEquipment(equipmentRepository.findById(dto.equipmentId()).orElseThrow(() -> new ResourceNotFoundException("Equipment not found")));
        acceptance.setPolicyName(dto.policyName());
        acceptance.setAcceptanceText(dto.acceptanceText());
        acceptance.setAcceptedAt(LocalDateTime.now());
        PolicyAcceptance saved = policyAcceptanceRepository.save(acceptance);
        auditService.record(saved.getUser(), AuditActions.POLICY_ACCEPTED, "PolicyAcceptance", saved.getId(), dto.policyName());
        return saved;
    }

    public List<StockSummaryDto> stockSummary() {
        return StockStatuses.ALL.stream().map(status -> new StockSummaryDto(status, stockRecordRepository.findByStatus(status).size())).toList();
    }
}
