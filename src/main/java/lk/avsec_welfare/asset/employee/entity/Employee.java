package lk.avsec_welfare.asset.employee.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.avsec_welfare.asset.censure.entitiy.Censure;
import lk.avsec_welfare.asset.common_asset.model.enums.*;
import lk.avsec_welfare.asset.common_asset.model.FileInfo;
import lk.avsec_welfare.asset.dependent.entity.DependentEmployee;
import lk.avsec_welfare.asset.designation.entity.Designation;
import lk.avsec_welfare.asset.employee_working_place.entity.EmployeeWorkingPlace;
import lk.avsec_welfare.asset.main_account.entity.ExpensesFund;
import lk.avsec_welfare.asset.instalment.entity.Instalment;

import lk.avsec_welfare.asset.promotion.entity.Promotion;
import lk.avsec_welfare.asset.qualification.entity.Qualification;
import lk.avsec_welfare.asset.working_place.entity.WorkingPlace;
import lk.avsec_welfare.util.audit.AuditEntity;
import lk.avsec_welfare.asset.employee.entity.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Employee" )
public class Employee extends AuditEntity {

  @Column( unique = true )
  private String epf;

  @Size( min = 5, message = "Your name cannot be accepted" )
  private String name;

  @Size( min = 2, message = "Your name cannot be accepted" )
  private String callingName;

  @Size( max = 12, min = 10, message = "NIC number is contained numbers between 9 and X/V or 12 " )
  @Column( unique = true )
  private String nic;

  @Column( unique = true )
  private String departmentIdNumber;

  @Size( max = 10, message = "Mobile number length should be contained 10 and 9" )
  private String mobileOne;

  @Size( max = 10, message = "Mobile number length should be contained 10 and 9" )
  private String mobileTwo;

  @Size( max = 10, message = "Residence number length should be contained 10 and 9" )
  private String land;

  private String fullName;

  private String nearestPoliceStation;

  @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL", length = 255 )
  private String temporaryAddress;

  private String residenceNo;

  @Size( max = 10, message = "Mobile number length should be contained 10 and 9" )
  private String officeNo;

  @Size( max = 10, message = "Mobile number length should be contained 10 and 9" )
  private String emergencyContactNo;

  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private String appoimentDate;

  private String intakeNo;

  @Column( unique = true )
  private String medicleNo;

  @Enumerated( EnumType.STRING )
  private WelfarePosition welfarePosition;

  @Enumerated( EnumType.STRING )
  private Nationality nationality;

  @Enumerated( EnumType.STRING )
  private Religion religion;

  @Enumerated( EnumType.STRING )
  private UniformType uniformType;

  @Column( unique = true )
  private String email;

  private String officeEmail;

  @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL", length = 255 )
  private String address;

  @Enumerated( EnumType.STRING )
  private Title title;

  @Enumerated( EnumType.STRING )
  private Gender gender;

  @Enumerated( EnumType.STRING )
  private BloodGroup bloodGroup;

  @Enumerated( EnumType.STRING )
  private CivilStatus civilStatus;

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;

  @Enumerated( EnumType.STRING )
  private EmployeeStatus employeeStatus;

  @Enumerated( EnumType.STRING )
  private BoardOfDirectors boardOfDirectors;

  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate dateOfBirth;

  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate dateOfAssignment;

  @ManyToOne
  private WorkingPlace workingPlace;

  @ManyToOne
  private Designation designation;

  @OneToMany( mappedBy = "employee" )
  private List< ExpensesFund > expensesFunds;

  @OneToMany( mappedBy = "employee" )
  private List< Instalment > instalments;

  @OneToMany( mappedBy = "employee" )
  private List< Promotion > promotions;

  @OneToMany( mappedBy = "employee" )
  private List< Qualification > qualifications;

  @OneToMany( mappedBy = "employee" )
  private List<Censure> censures;

  @OneToMany( mappedBy = "employeeOne" )
  private List< DependentEmployee > dependentEmployees;

  @OneToMany( mappedBy = "employee" )
  private List< EmployeeWorkingPlace > employeeWorkingPlaces;

  @Transient
  private MultipartFile file;

  @Transient
  private FileInfo fileInfo;

  @Transient
  private List< String > removeImages;

  @Transient
  private List< FileInfo > fileInfos;


}
