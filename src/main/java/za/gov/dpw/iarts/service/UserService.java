package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.gov.dpw.iarts.constants.RoleNames;
import za.gov.dpw.iarts.dto.CreateUserRequest;
import za.gov.dpw.iarts.exception.ResourceNotFoundException;
import za.gov.dpw.iarts.entity.Department;
import za.gov.dpw.iarts.entity.Role;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.constants.AuditActions;
import za.gov.dpw.iarts.repository.DepartmentRepository;
import za.gov.dpw.iarts.repository.RoleRepository;
import za.gov.dpw.iarts.repository.UserRepository;
import java.util.HashSet;
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

    @Transactional
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
        Set<String> roleNames = request.roles() == null || request.roles().isEmpty() ? Set.of(RoleNames.END_USER) : request.roles();
        user.setRoles(findRoles(roleNames));
        User saved = userRepository.save(user);
        auditService.record(saved, AuditActions.USER_CREATED, "User", saved.getId(), "User created");
        return saved;
    }

    @Transactional
    public void seedDefaultUsers() {
        ensureSeedUser("requestee", "requestee@iarts.local", "Requestee User", "REQ-001", "Requestee@123", Set.of(RoleNames.END_USER));
        ensureSeedUser("technician", "technician@iarts.local", "IARTS Technician", "TECH-001", "Technician@123", Set.of(RoleNames.TECHNICIAN));
        ensureSeedUser("storeroom", "storeroom@iarts.local", "ICT Storeroom User", "STORE-001", "Storeroom@123", Set.of(RoleNames.ICT_STOREROOM));
        ensureSeedUser("assets", "assets@iarts.local", "Asset Management User", "ASSET-001", "Assets@123", Set.of(RoleNames.ASSET_MANAGEMENT));
    }

    private User ensureSeedUser(String username, String email, String fullName, String employeeNumber, String password, Set<String> roleNames) {
        Set<Role> roles = findRoles(roleNames);
        User user = userRepository.findByUsername(username).orElseGet(User::new);
        boolean isNew = user.getId() == null;

        user.setUsername(username);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setEmployeeNumber(employeeNumber);
        user.setActive(true);
        user.setRoles(new HashSet<>(roles));
        if (isNew) {
            user.setPassword(passwordEncoder.encode(password));
        }
        return userRepository.save(user);
    }

    private Set<Role> findRoles(Set<String> roleNames) {
        return roleNames.stream()
                .map(name -> roleRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Role not found: " + name)))
                .collect(Collectors.toSet());
    }
}
