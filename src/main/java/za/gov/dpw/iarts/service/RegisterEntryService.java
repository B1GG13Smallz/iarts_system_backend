package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.constants.RoleNames;
import za.gov.dpw.iarts.dto.RegisterEntryDto;
import za.gov.dpw.iarts.dto.RegisterEntryResponseDto;
import za.gov.dpw.iarts.dto.RegisterSignatureDto;
import za.gov.dpw.iarts.dto.StoresOfficialSignatureDto;
import za.gov.dpw.iarts.entity.RegisterEntry;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.repository.RegisterEntryRepository;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegisterEntryService {
    private static final Set<String> REGISTER_TYPES = Set.of(
            "Permanent Issuing Equipment Register",
            "Loaning of ICT Equipment Register",
            "Loaning Technicians Set-up Register",
            "Unit Storage Register"
    );

    private final RegisterEntryRepository registerEntryRepository;

    public RegisterEntry create(User createdBy, RegisterEntryDto dto) {
        if (!REGISTER_TYPES.contains(dto.registerType())) {
            throw new IllegalArgumentException("Register type is invalid");
        }

        RegisterEntry entry = new RegisterEntry();
        entry.setCreatedBy(createdBy);
        entry.setRegisterType(dto.registerType());
        entry.setDateOut(parseDate(dto.dateOut()));
        entry.setItemDescription(dto.itemDescription());
        entry.setSerialNumber(dto.serialNumber());
        entry.setBarCode(dto.barCode());
        entry.setOrderNumber(dto.orderNumber());
        entry.setUserFullName(dto.userFullName());
        entry.setExtension(dto.extension());
        entry.setRoomNumber(dto.roomNumber());
        entry.setUserSignatureFileName(dto.userSignOut().fileName());
        entry.setUserSignatureContentType(dto.userSignOut().contentType());
        entry.setUserSignatureData(decodeSignature(dto.userSignOut()));
        applyStoresOfficialSignature(entry, dto.storesOfficialName(), dto.storesOfficialSignOut());
        entry.setComment(dto.comment());
        return registerEntryRepository.save(entry);
    }

    public List<RegisterEntryResponseDto> findByRegisterType(String registerType) {
        if (!REGISTER_TYPES.contains(registerType)) {
            throw new IllegalArgumentException("Register type is invalid");
        }

        return registerEntryRepository.findByRegisterTypeOrderByCreatedAtDesc(registerType)
                .stream()
                .map(RegisterEntryResponseDto::from)
                .toList();
    }

    public RegisterEntryResponseDto signStoresOfficial(User user, Long id, StoresOfficialSignatureDto dto) {
        if (!hasRole(user, RoleNames.ICT_STOREROOM) && !hasRole(user, RoleNames.ADMIN)) {
            throw new IllegalArgumentException("Only storeroom officials can sign register entries");
        }

        RegisterEntry entry = registerEntryRepository.findWithCreatedByById(id)
                .orElseThrow(() -> new IllegalArgumentException("Register entry not found"));
        applyStoresOfficialSignature(entry, dto.storesOfficialName(), dto.storesOfficialSignOut());
        return RegisterEntryResponseDto.from(registerEntryRepository.save(entry));
    }

    private void applyStoresOfficialSignature(RegisterEntry entry, String storesOfficialName, RegisterSignatureDto signature) {
        entry.setStoresOfficialName(storesOfficialName);

        if (signature == null) {
            return;
        }

        entry.setStoresOfficialSignatureFileName(signature.fileName());
        entry.setStoresOfficialSignatureContentType(signature.contentType());
        entry.setStoresOfficialSignatureData(decodeSignature(signature));
    }

    private boolean hasRole(User user, String roleName) {
        return user.getRoles().stream().anyMatch(role -> roleName.equals(role.getName()));
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Date out is invalid");
        }
    }

    private byte[] decodeSignature(RegisterSignatureDto signature) {
        try {
            return Base64.getDecoder().decode(signature.base64());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Signature file is not valid base64");
        }
    }
}
