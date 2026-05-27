package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "equipment")
public class Equipment extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String assetTag;
    @Column(unique = true)
    private String serialNumber;
    @Column(nullable = false)
    private String assetType;
    private String make;
    private String model;
    private String location;
    private String netTrackReference;
    private boolean laptopPolicyRequired;
}
