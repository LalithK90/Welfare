package lk.avsec_welfare.asset.briefing.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.avsec_welfare.asset.grievances.entity.Enum.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Briefing")
public class Briefing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Size(min = 2, max = 60, message = "Your name length should be 13")
    private String name;

    private String toWhom;
    private String subject;

    private String notices;


    @Enumerated(EnumType.STRING)
    private Priority priority;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate effDate;
//id, name, toWhom, notices, priority date
}
