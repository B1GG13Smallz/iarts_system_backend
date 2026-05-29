package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true, length = 150)
    private String name;
    @Column(name = "code", length = 50)
    private String code;
}
