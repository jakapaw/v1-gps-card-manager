package dev.jakapaw.giftcard.seriesmanager.application.event;

public class GiftcardDeducted {

    private final String serialNumber;
    private final double balance;

    public GiftcardDeducted(String serialNumber, double balance) {
        this.serialNumber = serialNumber;
        this.balance = balance;
    }
}
