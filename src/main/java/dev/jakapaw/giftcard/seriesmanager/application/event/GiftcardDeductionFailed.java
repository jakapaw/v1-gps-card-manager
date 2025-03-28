package dev.jakapaw.giftcard.seriesmanager.application.event;

import java.util.EventObject;

import lombok.Getter;

@Getter
public class GiftcardDeductionFailed extends EventObject {

    private final String paymentId;
    private final String giftcardSerialNumber;
    private final double billAmount;
    public GiftcardDeductionFailed(Object source, String paymentId, String giftcardSerialNumber, double billAmount) {
        super(source);
        this.paymentId = paymentId;
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.billAmount = billAmount;
    }
}