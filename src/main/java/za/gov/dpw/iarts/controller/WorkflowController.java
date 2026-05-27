package za.gov.dpw.iarts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import za.gov.dpw.iarts.dto.DamageReportDto;
import za.gov.dpw.iarts.dto.MovementRequestDto;
import za.gov.dpw.iarts.dto.RemovalRequestDto;
import za.gov.dpw.iarts.dto.TakeHomeRequestDto;
import za.gov.dpw.iarts.entity.DamageReport;
import za.gov.dpw.iarts.entity.MovementRequest;
import za.gov.dpw.iarts.entity.RemovalRequest;
import za.gov.dpw.iarts.entity.TakeHomeRequest;
import za.gov.dpw.iarts.service.WorkflowService;

@RestController
@RequiredArgsConstructor
public class WorkflowController {
    private final WorkflowService workflowService;

    @PostMapping("/api/movements")
    public MovementRequest movement(@Valid @RequestBody MovementRequestDto dto) {
        return workflowService.createMovement(dto);
    }

    @PostMapping("/api/removals")
    public RemovalRequest removal(@Valid @RequestBody RemovalRequestDto dto) {
        return workflowService.createRemoval(dto);
    }

    @PostMapping("/api/take-home")
    public TakeHomeRequest takeHome(@Valid @RequestBody TakeHomeRequestDto dto) {
        return workflowService.createTakeHome(dto);
    }

    @PostMapping("/api/damage")
    public DamageReport damage(@Valid @RequestBody DamageReportDto dto) {
        return workflowService.reportDamage(dto);
    }
}
