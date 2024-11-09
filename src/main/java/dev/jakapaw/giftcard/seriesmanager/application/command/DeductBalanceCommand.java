package dev.jakapaw.giftcard.seriesmanager.application.command;

import lombok.Getter;

import java.util.EventObject;

@Getter
public class DeductBalanceCommand extends EventObject {

    private final String serialNumber;
    private final double billed;

    public DeductBalanceCommand(Object source, String serialNumber, double billed) {
        super(source);
        this.serialNumber = serialNumber;
        this.billed = billed;
    }
}
