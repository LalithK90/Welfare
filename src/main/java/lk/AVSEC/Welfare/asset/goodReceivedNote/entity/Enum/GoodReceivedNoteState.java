package lk.AVSEC.Welfare.asset.goodReceivedNote.entity.Enum;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GoodReceivedNoteState {
    NOT_PAID(" Not paid"),
    PAID(" Paid");
    private final String goodReceivedNoteState;
}
