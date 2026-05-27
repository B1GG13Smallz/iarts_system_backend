package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.CreateUserRequest;
import za.gov.dpw.iarts.exception.ResourceNotFoundException;
import za.gov.dpw.iarts.entity.Department;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.constants.AuditActions;
import za.gov.dpw.iarts.repository.DepartmentRepository;
import za.gov.dpw.iarts.repository.RoleRepository;
import za.gov.dpw.iarts.repository.UserRepository;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;

    public User create(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFullName(request.fullName());
        user.setEmployeeNumber(request.employeeNumber());
        if (request.departmentId() != null) {
            Department department = departmentRepository.findById(request.departmentId()).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            user.setDepartment(department);
        }
        Set<String> roleNames = request.roles() == null || request.roles().isEmpty() ? Set.of("END_USER") : request.roles();
        user.setRoles(roleNames.stream().map(name -> roleRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Role not found: " + name))).collect(Collectors.toSet()));
        User saved = userRepository.save(user);
        auditService.record(saved, AuditActions.USER_CREATED, "User", saved.getId(), "User created");
        return saved;
    }
}
