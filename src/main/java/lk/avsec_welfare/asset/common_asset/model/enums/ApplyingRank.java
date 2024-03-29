package lk.avsec_welfare.asset.common_asset.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplyingRank {
    ASP("Assistant Superintendent of Police"),
    WASP("Woman Assistant Superintendent of Police"),
    SI("Sub Inspector"),
    WSI("Woman Sub Inspector"),
    PC("Police Constable"),
    WPC("Woman Police Constable"),
    PCD("Police Constable Driver");

    private final String applyingRank;
}


