package dev.jakapaw.giftcard.seriesmanager.application.event;

import lombok.Getter;

@Getter
public class GiftcardDeductionFailed {

    private final String giftcardSerialNumber;
    private final double billAmount;

    public GiftcardDeductionFailed(String giftcardSerialNumber, double billAmount) {
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.billAmount = billAmount;
    }
}
