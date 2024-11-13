package dev.jakapaw.giftcard.seriesmanager.application.event;

import lombok.Getter;

@Getter
public class GiftcardDeducted {

    private final String serialNumber;
    private final double finalBalance;

    public GiftcardDeducted(String serialNumber, double finalBalance) {
        this.serialNumber = serialNumber;
        this.finalBalance = finalBalance;
    }
}
