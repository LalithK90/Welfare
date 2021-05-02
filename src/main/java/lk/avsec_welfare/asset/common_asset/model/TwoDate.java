package lk.avsec_welfare.asset.common_asset.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TwoDate {
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate startDate;
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate endDate;
    private Integer id;
    private Integer count;
}
