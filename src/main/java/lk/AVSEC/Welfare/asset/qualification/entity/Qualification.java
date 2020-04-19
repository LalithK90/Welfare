package lk.AVSEC.Welfare.asset.qualification.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.Province;
import lk.AVSEC.Welfare.asset.workingPlace.entity.WorkingPlace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Qualification")
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2, max = 60, message = "Your name length should be 13")
    private String name;

    private String institute;

    private String grade;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completeDate;
    /*@Enumerated(EnumType.STRING)
    private Province province;*/


/*    @OneToMany(mappedBy = "district")
    private List<WorkingPlace> workingPlaces;*/
}
