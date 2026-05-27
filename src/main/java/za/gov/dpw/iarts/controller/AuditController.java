package za.gov.dpw.iarts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.gov.dpw.iarts.entity.AuditLog;
import za.gov.dpw.iarts.service.AuditService;
import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {
    private final AuditService auditService;

    @GetMapping("/logs")
    public List<AuditLog> logs() {
        return auditService.findAll();
    }
}
