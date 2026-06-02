package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.PermissionRemovalDto;
import za.gov.dpw.iarts.dto.PermissionRemovalSignatureDto;
import za.gov.dpw.iarts.entity.PermissionRemoval;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.repository.PermissionRemovalRepository;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PermissionRemovalService {
    private final PermissionRemovalRepository permissionRemovalRepository;

    public PermissionRemoval create(User createdBy, PermissionRemovalDto dto) {
        if (isBlank(dto.officialName()) || isBlank(dto.equipmentDescription()) || isBlank(dto.barCode()) || isBlank(dto.serialNumber())) {
            throw new IllegalArgumentException("Official name, equipment description, bar code and serial number are required");
        }

        PermissionRemoval removal = new PermissionRemoval();
        removal.setCreatedBy(createdBy);
        removal.setOfficialName(dto.officialName());
        removal.setUnitDirectorateBranch(dto.unitDirectorateBranch());
        removal.setTelephoneNumber(dto.telephoneNumber());
        removal.setIdentityOrPersalNumber(dto.identityOrPersalNumber());
        removal.setRemovalReason(dto.removalReason());
        applySignature(dto.officialSignature(), removal::setOfficialSignatureFileName, removal::setOfficialSignatureContentType, removal::setOfficialSignatureData);
        removal.setEquipmentDescription(dto.equipmentDescription());
        removal.setBarCode(dto.barCode());
        removal.setSerialNumber(dto.serialNumber());
        removal.setCurrentLocation(dto.currentLocation());
        removal.setPeriod(dto.period());
        removal.setNewLocation(dto.newLocation());
        applySignature(dto.ictSignature(), removal::setIctSignatureFileName, removal::setIctSignatureContentType, removal::setIctSignatureData);
        removal.setIctDate(parseOptionalDate(dto.ictDate(), "ICT date"));
        applySignature(dto.mamSignature(), removal::setMamSignatureFileName, removal::setMamSignatureContentType, removal::setMamSignatureData);
        removal.setMamDate(parseOptionalDate(dto.mamDate(), "MAM date"));
        applySignature(dto.securitySignature(), removal::setSecuritySignatureFileName, removal::setSecuritySignatureContentType, removal::setSecuritySignatureData);
        removal.setSecurityDate(parseOptionalDate(dto.securityDate(), "Security date"));
        return permissionRemovalRepository.save(removal);
    }

    private void applySignature(
            PermissionRemovalSignatureDto signature,
            StringSetter fileNameSetter,
            StringSetter contentTypeSetter,
            BytesSetter dataSetter
    ) {
        if (signature == null) {
            return;
        }

        fileNameSetter.set(signature.fileName());
        contentTypeSetter.set(signature.contentType());
        dataSetter.set(decodeSignature(signature.base64()));
    }

    private byte[] decodeSignature(String signatureBase64) {
        try {
            return Base64.getDecoder().decode(signatureBase64);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Signature file is not valid base64");
        }
    }

    private LocalDate parseOptionalDate(String date, String label) {
        if (date == null || date.isBlank()) {
            return null;
        }

        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(label + " is invalid");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private interface StringSetter {
        void set(String value);
    }

    private interface BytesSetter {
        void set(byte[] value);
    }
}
