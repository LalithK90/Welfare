package lk.avsec_welfare.asset.common_asset.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicantStatus {
    P("Pending"),
    A("Approved"),
    NTA("Not Approved"),
    FST("First Interview"),
    FSTP("First Interview Pass"),
    FSTR("First Interview Reject"),
    SND("Second Interview"),
    SNDP("Second Interview Pass"),
    SNDR("Second Interview Reject"),
    TND("Third Interview"),
    TNDP("Third Interview Pass"),
    TNDR("Third Interview Reject"),
    FTH("Forth Interview"),
    FTHP("Forth Interview Pass"),
    FTHR("Forth Interview Reject");
    private final String applicantStatus;
}
