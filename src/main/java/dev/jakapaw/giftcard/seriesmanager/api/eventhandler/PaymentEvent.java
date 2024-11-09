package dev.jakapaw.giftcard.seriesmanager.api.eventhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentEvent {

    private String serialNumber;
    private double billed;
}
