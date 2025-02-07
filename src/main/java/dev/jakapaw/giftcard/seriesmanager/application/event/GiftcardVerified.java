package dev.jakapaw.giftcard.seriesmanager.application.event;

import java.util.EventObject;

import lombok.Getter;

@Getter
public class GiftcardVerified extends EventObject {

    private final String paymentId;
    private final String serialNumber;
    private final double billAmount;
    private final boolean isExist;
    private final boolean isBalanceAvailable;

    public GiftcardVerified(Object source, String paymentId, String serialNumber, double billAmount, boolean isExist,
            boolean isBalanceAvailable) {
        super(source);
        this.paymentId = paymentId;
        this.serialNumber = serialNumber;
        this.billAmount = billAmount;
        this.isExist = isExist;
        this.isBalanceAvailable = isBalanceAvailable;
    }
}
