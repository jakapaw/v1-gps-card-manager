package dev.jakapaw.giftcard.seriesmanager.application.event;

public class GiftcardVerified {

    private final String serialNumber;
    private final boolean isExist;
    private final boolean isBalanceAvailable;
    
    public GiftcardVerified(String serialNumber, boolean isExist, boolean isBalanceAvailable) {
        this.serialNumber = serialNumber;
        this.isExist = isExist;
        this.isBalanceAvailable = isBalanceAvailable;
    }
}
