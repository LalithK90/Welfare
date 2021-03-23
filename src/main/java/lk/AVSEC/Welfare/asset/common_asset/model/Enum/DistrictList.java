package lk.AVSEC.Welfare.asset.common_asset.model.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DistrictList {
    CB("Colombo"),
    GP("Gampaha"),
    KT("Kaluthara"),
    JF("Jaffna"),
    KI("Kilinochchiya"),
    VA("Vauniyava"),
    MV("Mulative"),
    MA("Mannar"),
    GL("Galle"),
    MR("Mattar"),
    HT("Hambantota"),
    AM("Ampara"),
    BT("Batticaloa"),
    TM("Trincomale"),
    AP("Anuradhapura"),
    PN("Polonnaruwa"),
    KD("Kandy"),
    NE("Nuwara Eliya"),
    MT("Matale"),
    KN("Kurunrgala"),
    PT("Puttlam"),
    BU("Badulla"),
    MN("Monaragala"),
    RP("Rathnapura"),
    KG("Kegalle");
    private final String districtList;
}
