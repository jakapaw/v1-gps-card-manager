package dev.jakapaw.giftcard.seriesmanager.application.command;

import lombok.Getter;

import java.util.EventObject;

@Getter
public class ProcessPaymentCommand extends EventObject {

    private final String giftcardSerialNumber;
    private final double billAmount;

    public ProcessPaymentCommand(Object source, String giftcardSerialNumber, double billAmount) {
        super(source);
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.billAmount = billAmount;
    }
}
