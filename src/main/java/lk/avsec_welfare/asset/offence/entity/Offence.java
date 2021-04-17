package lk.avsec_welfare.asset.offence.entity;

import com.fasterxml.jackson.annotation.JsonFilter;

import lk.avsec_welfare.asset.censure.entitiy.Censure;
import lk.avsec_welfare.asset.offence.entity.enums.OffenceType;
import lk.avsec_welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Offence")
public class Offence extends AuditEntity {

    @Column(length = 800)
    private String name;

    @Enumerated(EnumType.STRING)
    private OffenceType offenceType;


    @OneToMany(mappedBy = "offence")
    private List<Censure> censures;
}
