package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.DamageReportDto;
import za.gov.dpw.iarts.dto.MovementRequestDto;
import za.gov.dpw.iarts.dto.RemovalRequestDto;
import za.gov.dpw.iarts.dto.TakeHomeRequestDto;
import za.gov.dpw.iarts.exception.ResourceNotFoundException;
import za.gov.dpw.iarts.entity.DamageReport;
import za.gov.dpw.iarts.entity.MovementRequest;
import za.gov.dpw.iarts.entity.RemovalRequest;
import za.gov.dpw.iarts.entity.TakeHomeRequest;
import za.gov.dpw.iarts.constants.AuditActions;
import za.gov.dpw.iarts.repository.DamageReportRepository;
import za.gov.dpw.iarts.repository.EquipmentRepository;
import za.gov.dpw.iarts.repository.MovementRequestRepository;
import za.gov.dpw.iarts.repository.RemovalRequestRepository;
import za.gov.dpw.iarts.repository.TakeHomeRequestRepository;
import za.gov.dpw.iarts.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class WorkflowService {
    private final MovementRequestRepository movementRepository;
    private final RemovalRequestRepository removalRepository;
    private final TakeHomeRequestRepository takeHomeRepository;
    private final DamageReportRepository damageRepository;
    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;
    private final AuditService auditService;

    public MovementRequest createMovement(MovementRequestDto dto) {
        MovementRequest movement = new MovementRequest();
        movement.setRequester(userRepository.findById(dto.requesterId()).orElseThrow(() -> new ResourceNotFoundException("Requester not found")));
        movement.setEquipment(equipmentRepository.findById(dto.equipmentId()).orElseThrow(() -> new ResourceNotFoundException("Equipment not found")));
        movement.setFromLocation(dto.fromLocation());
        movement.setToLocation(dto.toLocation());
        movement.setClientConfirmed(dto.clientConfirmed());
        movement.setRemarks(dto.remarks());
        if (dto.technicianId() != null) movement.setTechnician(userRepository.findById(dto.technicianId()).orElseThrow(() -> new ResourceNotFoundException("Technician not found")));
        if (dto.assetManagementVerifierId() != null) movement.setAssetManagementVerifier(userRepository.findById(dto.assetManagementVerifierId()).orElseThrow(() -> new ResourceNotFoundException("Verifier not found")));
        MovementRequest saved = movementRepository.save(movement);
        auditService.record(saved.getRequester(), AuditActions.MOVEMENT_CREATED, "MovementRequest", saved.getId(), "Movement requested");
        return saved;
    }

    public RemovalRequest createRemoval(RemovalRequestDto dto) {
        RemovalRequest removal = new RemovalRequest();
        removal.setRequester(userRepository.findById(dto.requesterId()).orElseThrow(() -> new ResourceNotFoundException("Requester not found")));
        removal.setEquipment(equipmentRepository.findById(dto.equipmentId()).orElseThrow(() -> new ResourceNotFoundException("Equipment not found")));
        removal.setReason(dto.reason());
        removal.setRemovalDate(dto.removalDate());
        removal.setExpectedReturnDate(dto.expectedReturnDate());
        removal.setComments(dto.comments());
        if (dto.ictApproverId() != null) removal.setIctApprover(userRepository.findById(dto.ictApproverId()).orElseThrow(() -> new ResourceNotFoundException("ICT approver not found")));
        if (dto.mamApproverId() != null) removal.setMamApprover(userRepository.findById(dto.mamApproverId()).orElseThrow(() -> new ResourceNotFoundException("MAM approver not found")));
        if (dto.securityValidatorId() != null) removal.setSecurityValidator(userRepository.findById(dto.securityValidatorId()).orElseThrow(() -> new ResourceNotFoundException("Security validator not found")));
        RemovalRequest saved = removalRepository.save(removal);
        auditService.record(saved.getRequester(), AuditActions.REMOVAL_CREATED, "RemovalRequest", saved.getId(), "Removal requested");
        return saved;
    }

    public TakeHomeRequest createTakeHome(TakeHomeRequestDto dto) {
        TakeHomeRequest takeHome = new TakeHomeRequest();
        takeHome.setRequester(userRepository.findById(dto.requesterId()).orElseThrow(() -> new ResourceNotFoundException("Requester not found")));
        takeHome.setEquipment(equipmentRepository.findById(dto.equipmentId()).orElseThrow(() -> new ResourceNotFoundException("Equipment not found")));
        takeHome.setRequesterCategory(dto.requesterCategory());
        takeHome.setStartDate(dto.startDate());
        takeHome.setEndDate(dto.endDate());
        takeHome.setReason(dto.reason());
        if (dto.approverId() != null) takeHome.setApprover(userRepository.findById(dto.approverId()).orElseThrow(() -> new ResourceNotFoundException("Approver not found")));
        return takeHomeRepository.save(takeHome);
    }

    public DamageReport reportDamage(DamageReportDto dto) {
        DamageReport damage = new DamageReport();
        damage.setEquipment(equipmentRepository.findById(dto.equipmentId()).orElseThrow(() -> new ResourceNotFoundException("Equipment not found")));
        damage.setReportedBy(userRepository.findById(dto.reportedById()).orElseThrow(() -> new ResourceNotFoundException("Reporter not found")));
        damage.setIncidentDescription(dto.incidentDescription());
        damage.setWarrantyReference(dto.warrantyReference());
        damage.setFollowUpNotes(dto.followUpNotes());
        DamageReport saved = damageRepository.save(damage);
        auditService.record(saved.getReportedBy(), AuditActions.DAMAGE_REPORTED, "DamageReport", saved.getId(), "Damage reported");
        return saved;
    }
}
