package dev.jakapaw.giftcard.seriesmanager.infrastructure.broker.eventhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SharedPaymentEvent {

    private String giftcardSerialNumber;
    private double billAmount;
    private String paymentStatus;
}
