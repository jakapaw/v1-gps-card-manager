package dev.jakapaw.giftcard.seriesmanager.application.event;

import java.util.EventObject;

import lombok.Getter;

@Getter
public class GiftcardCreated extends EventObject {
    
    private final String giftcardSerialNumber;
    private final double initialBalance;
    
    public GiftcardCreated(Object source, String giftcardSerialNumber, double initialBalance) {
        super(source);
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.initialBalance = initialBalance;
    }
}
