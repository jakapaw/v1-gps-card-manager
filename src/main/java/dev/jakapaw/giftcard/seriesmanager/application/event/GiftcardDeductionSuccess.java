package dev.jakapaw.giftcard.seriesmanager.application.event;

import java.util.EventObject;

import lombok.Getter;

@Getter
public class GiftcardDeductionSuccess extends EventObject {

    private final String paymentId;
    private final String giftcardSerialNumber;
    private final double billAmount;
    private final double finalBalance;
    public GiftcardDeductionSuccess(Object source, String paymentId, String giftcardSerialNumber, double billAmount,
            double finalBalance) {
        super(source);
        this.paymentId = paymentId;
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.billAmount = billAmount;
        this.finalBalance = finalBalance;
    }
}
