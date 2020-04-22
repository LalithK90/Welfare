package lk.AVSEC.Welfare.asset.dependency.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.dependency.entity.Enum.Relationship;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Dependency")
public class Dependency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2, max = 60, message = "Your name length should be 13")
    private String name;

    @Enumerated(EnumType.STRING)
    private Relationship relationship;
  /*  private String relationship;*/

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String nic;

    /*@Enumerated(EnumType.STRING)
    private CurrentStatus currentStatus;*/
    private String currentStatus;

    private String remark;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private LocalDate completeDate;
    /*@Enumerated(EnumType.STRING)
    private Province province;*/
   // Name    Relationship    DoB   NIC   Current Status Remark

/*    @OneToMany(mappedBy = "district")
    private List<WorkingPlace> workingPlaces;*/
}
