package dev.jakapaw.giftcard.seriesmanager.application.command;

import java.util.EventObject;

import lombok.Getter;

@Getter
public class DeductBalanceCommand extends EventObject {

    private final String paymentId;
    private final String giftcardSerialNumber;
    private final double billAmount;
    public DeductBalanceCommand(Object source, String paymentId, String giftcardSerialNumber, double billAmount) {
        super(source);
        this.paymentId = paymentId;
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.billAmount = billAmount;
    }
}
