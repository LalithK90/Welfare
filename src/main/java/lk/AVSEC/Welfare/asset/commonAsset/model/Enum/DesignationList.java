package lk.AVSEC.Welfare.asset.commonAsset.model.Enum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DesignationList {
    SM("Sentryman"),
    PM("Patrolman"),
    SPM("Snr. Patrolman"),
    PL("Patrol Leader"),
    SPL("Snr. Patrol Leader"),
    SO("Security Officer"),
    DSM("Deputy Security Marshal"),
    SMsl("Security Marshal"),
    SSMsl("Snr. Security Marshal"),
    DHOSS("Deputy Head Of Security Services"),
    HOSS("Head of Security Services"),
    Stry("Secretary"),
    MA("Management Assistant"),
    Amr("Armorer"),
    AS("Arakshaka Sahayaka"),
    PKS("Podu Karya Sahayaka"),
    PKSe("Podu Karya Sewaka"),
    OSA("Office Service Assistant"),
    Driver("Driver");

    private final String designationList;


}
