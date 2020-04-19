package lk.AVSEC.Welfare.asset.commonAsset.model.Enum;

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
