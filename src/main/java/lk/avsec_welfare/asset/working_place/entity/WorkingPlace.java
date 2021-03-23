package lk.avsec_welfare.asset.working_place.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.district.entity.District;
import lk.avsec_welfare.asset.employee_working_place.entity.EmployeeWorkingPlace;
import lk.avsec_welfare.asset.working_place.entity.enums.AirportType;
import lk.avsec_welfare.asset.working_place.entity.enums.ShortName;
import lk.avsec_welfare.asset.working_place.entity.enums.WorkingPlaceSection;
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
@JsonFilter("WorkingPlace")
public class WorkingPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String address;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private ShortName shortName;

    @Enumerated(EnumType.STRING)
    private AirportType airportType;

    @Enumerated(EnumType.STRING)
    private LiveDead liveDead;


    @Enumerated(EnumType.STRING)
    private WorkingPlaceSection workingPlaceSection;

    @ManyToOne
    private District district;

    @OneToMany
    private List<EmployeeWorkingPlace> employeeWorkingPlaces;


}
