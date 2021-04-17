package lk.avsec_welfare.asset.censure_file.entity;


import lk.avsec_welfare.asset.censure.entitiy.Censure;
import lk.avsec_welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CensureFiles extends AuditEntity {

    private String name, mimeType,newName;

    @Column(unique = true)
    private String newId;

    @Lob
    private byte[] pic;

    public CensureFiles(String name, String mimeType, byte[] pic, String newName, String newId) {
        this.name = name;
        this.mimeType = mimeType;
        this.pic = pic;
        this.newName = newName;
        this.newId = newId;
    }

    @ManyToOne
    private Censure censure;

}
