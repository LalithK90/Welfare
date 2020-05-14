package lk.AVSEC.Welfare.asset.employee.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.*;
import lk.AVSEC.Welfare.asset.commonAsset.model.FileInfo;
import lk.AVSEC.Welfare.asset.designation.entity.Designation;
import lk.AVSEC.Welfare.asset.employee.entity.Enum.EmployeeStatus;
import lk.AVSEC.Welfare.asset.employee.entity.Enum.Nationality;
import lk.AVSEC.Welfare.asset.employee.entity.Enum.UniformType;
import lk.AVSEC.Welfare.asset.finance.entity.ReceivingFund;
import lk.AVSEC.Welfare.asset.finance.entity.ExpensesFund;
import lk.AVSEC.Welfare.asset.message.entity.EmailMessage;
import lk.AVSEC.Welfare.asset.workingPlace.entity.WorkingPlace;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Employee")
@ToString
public class Employee extends AuditEntity {

    @Column(unique = true)
    private String epf;

    @Size(min = 5, message = "Your name cannot be accepted")
    private String name;


    private String callingName;

    @Size(max = 12, min = 10, message = "NIC number is contained numbers between 9 and X/V or 12 ")
    @Column(unique = true)
    private String nic;

    @Column(unique = true)
    private String departmentIdNumber;

    @Size(max = 10, message = "Mobile number length should be contained 10 and 9")
    private String mobileOne;

    @Size(max = 10, message = "Mobile number length should be contained 10 and 9")
    private String mobileTwo;

    @Size(max = 10, message = "Residence number length should be contained 10 and 9")
    private String land;

    private String fullName;
    private String nearestPoliceStation;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL", length = 255)
    private String temporaryAddress;

    private String residenceNo;

    @Size(max = 10, message = "Mobile number length should be contained 10 and 9")
    private String officeNo;

    @Size(max = 10, message = "Mobile number length should be contained 10 and 9")
    private String emergencyContactNo;

    private String position;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String appoimentDate;
    private String intakeNo;

    @Column(unique = true)
    private String medicleNo;

    @Enumerated(EnumType.STRING)
    private Nationality nationality;

    @Enumerated(EnumType.STRING)
    private Religion religion;

    @Enumerated(EnumType.STRING)
    private UniformType uniformType;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String officeEmail;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL", length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    private Title title;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @Enumerated(EnumType.STRING)
    private CivilStatus civilStatus;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus employeeStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfAssignment;

    @ManyToOne
    private WorkingPlace workingPlace;

    @ManyToOne
    private Designation designation;



    @OneToMany(mappedBy = "employee")
    private List<ExpensesFund> expensesFunds;

    @OneToMany(mappedBy = "employee")
    private List<ReceivingFund> receivingFunds;

    @ManyToMany(mappedBy = "employees")
    private List<EmailMessage> emailMessages;

    @Transient
    private MultipartFile file;

    @Transient
    private List<String> removeImages = new ArrayList<>();

    @Transient
    private List<FileInfo> fileInfos = new ArrayList<>();



}
