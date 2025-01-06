package dev.jakapaw.giftcard.seriesmanager.application.command;

import java.util.EventObject;

import lombok.Getter;

@Getter
public class VerifyPaymentCommand extends EventObject {
    
    private final String giftcardSerialNumber;
    private final double billAmount;
    
    public VerifyPaymentCommand(Object source, String giftcardSerialNumber, double billAmount) {
        super(source);
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.billAmount = billAmount;
    }
}
