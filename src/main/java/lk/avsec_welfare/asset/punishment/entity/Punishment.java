package lk.avsec_welfare.asset.punishment.entity;

import com.fasterxml.jackson.annotation.JsonFilter;

import lk.avsec_welfare.asset.censure.entitiy.Censure;
import lk.avsec_welfare.asset.punishment.entity.enums.PunishmentType;
import lk.avsec_welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Punishment")
public class Punishment extends AuditEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private PunishmentType punishmentType;

    @OneToMany(mappedBy = "punishment")
    private List<Censure> censures;
}
