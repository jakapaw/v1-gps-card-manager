package dev.jakapaw.giftcard.seriesmanager.application.event;

import java.util.EventObject;

public class GiftcardVerified extends EventObject {

    private final String serialNumber;
    private final boolean isExist;

    public GiftcardVerified(Object source, String serialNumber, boolean isExist) {
        super(source);
        this.serialNumber = serialNumber;
        this.isExist = isExist;
    }
}
