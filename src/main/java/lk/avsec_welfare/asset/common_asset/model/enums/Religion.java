package lk.avsec_welfare.asset.common_asset.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum Religion {
    BS("Buddhism"),
    HN("Hinduism"),
    IM("Islam"),
    CL("Christianity"),
    OT("Other");
        private final String religion;

}
