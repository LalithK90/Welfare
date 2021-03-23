package lk.AVSEC.Welfare.asset.working_place.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.district.entity.District;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.employee_working_place.entity.EmployeeWorkingPlace;
import lk.AVSEC.Welfare.asset.working_place.entity.Enum.AirportType;
import lk.AVSEC.Welfare.asset.working_place.entity.Enum.ShortName;
import lk.AVSEC.Welfare.asset.working_place.entity.Enum.WorkingPlaceSection;
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
    private WorkingPlaceSection workingPlaceSection;

    @ManyToOne
    private District district;

    @OneToMany
    private List<EmployeeWorkingPlace> employeeWorkingPlaces;


}