package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "intra_request_signatures")
public class IntraRequestSignature extends BaseEntity {
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false, unique = true)
    private IntraRequest request;

    @Column(name = "signature_date")
    private LocalDate signatureDate;

    @Column(name = "signature_file_name", length = 255)
    private String signatureFileName;

    @Column(name = "signature_content_type", length = 100)
    private String signatureContentType;

    @Lob
    @Column(name = "signature_data", columnDefinition = "LONGBLOB")
    private byte[] signatureData;
}
