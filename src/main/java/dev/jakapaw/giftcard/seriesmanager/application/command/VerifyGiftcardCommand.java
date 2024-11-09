package dev.jakapaw.giftcard.seriesmanager.application.command;

import lombok.Getter;

import java.util.EventObject;

@Getter
public class VerifyGiftcardCommand extends EventObject {

    private final String serialNumber;
    private boolean isExist;

    public VerifyGiftcardCommand(Object source, String serialNumber, boolean isExist) {
        super(source);
        this.serialNumber = serialNumber;
        this.isExist = isExist;
    }

    public void setExist(boolean value) {
        isExist = value;
    }
}
