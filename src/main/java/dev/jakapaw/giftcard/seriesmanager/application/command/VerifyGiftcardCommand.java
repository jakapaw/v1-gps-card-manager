package dev.jakapaw.giftcard.seriesmanager.application.command;

import java.util.EventObject;

import lombok.Getter;

@Getter
public class VerifyGiftcardCommand extends EventObject {
    
    private final String paymentId;
    private final String giftcardSerialNumber;
    private final double billAmount;
    public VerifyGiftcardCommand(Object source, String paymentId, String giftcardSerialNumber, double billAmount) {
        super(source);
        this.paymentId = paymentId;
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.billAmount = billAmount;
    }
}
