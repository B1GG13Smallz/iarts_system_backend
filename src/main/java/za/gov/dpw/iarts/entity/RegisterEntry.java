package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "register_entries")
public class RegisterEntry extends BaseEntity {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;

    @Column(name = "register_type", nullable = false, length = 100)
    private String registerType;

    @Column(name = "date_out", nullable = false)
    private LocalDate dateOut;

    @Column(name = "item_description", nullable = false, length = 500)
    private String itemDescription;

    @Column(name = "serial_number", nullable = false, length = 150)
    private String serialNumber;

    @Column(name = "bar_code", nullable = false, length = 150)
    private String barCode;

    @Column(name = "order_number", length = 150)
    private String orderNumber;

    @Column(name = "user_full_name", nullable = false, length = 255)
    private String userFullName;

    @Column(name = "extension", length = 50)
    private String extension;

    @Column(name = "room_number", length = 100)
    private String roomNumber;

    @Column(name = "user_signature_file_name", nullable = false, length = 255)
    private String userSignatureFileName;

    @Column(name = "user_signature_content_type", nullable = false, length = 100)
    private String userSignatureContentType;

    @Lob
    @Column(name = "user_signature_data", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] userSignatureData;

    @Column(name = "stores_official_name", length = 255)
    private String storesOfficialName;

    @Column(name = "stores_official_signature_file_name", length = 255)
    private String storesOfficialSignatureFileName;

    @Column(name = "stores_official_signature_content_type", length = 100)
    private String storesOfficialSignatureContentType;

    @Lob
    @Column(name = "stores_official_signature_data", columnDefinition = "LONGBLOB")
    private byte[] storesOfficialSignatureData;

    @Column(name = "comment", length = 2000)
    private String comment;
}
