package za.gov.dpw.iarts.dto;

import za.gov.dpw.iarts.entity.RegisterEntry;
import java.time.LocalDateTime;
import java.util.Base64;

public record RegisterEntryResponseDto(
        Long id,
        String registerType,
        String dateOut,
        String itemDescription,
        String serialNumber,
        String barCode,
        String orderNumber,
        String userFullName,
        String extension,
        String roomNumber,
        RegisterSignatureDto userSignOut,
        String storesOfficialName,
        RegisterSignatureDto storesOfficialSignOut,
        String comment,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static RegisterEntryResponseDto from(RegisterEntry entry) {
        return new RegisterEntryResponseDto(
                entry.getId(),
                entry.getRegisterType(),
                entry.getDateOut().toString(),
                entry.getItemDescription(),
                entry.getSerialNumber(),
                entry.getBarCode(),
                entry.getOrderNumber(),
                entry.getUserFullName(),
                entry.getExtension(),
                entry.getRoomNumber(),
                signature(entry.getUserSignatureFileName(), entry.getUserSignatureContentType(), entry.getUserSignatureData()),
                entry.getStoresOfficialName(),
                signature(entry.getStoresOfficialSignatureFileName(), entry.getStoresOfficialSignatureContentType(), entry.getStoresOfficialSignatureData()),
                entry.getComment(),
                entry.getCreatedBy().getUsername(),
                entry.getCreatedAt(),
                entry.getUpdatedAt()
        );
    }

    private static RegisterSignatureDto signature(String fileName, String contentType, byte[] data) {
        if (fileName == null || contentType == null || data == null || data.length == 0) {
            return null;
        }

        return new RegisterSignatureDto(fileName, contentType, Base64.getEncoder().encodeToString(data));
    }
}
