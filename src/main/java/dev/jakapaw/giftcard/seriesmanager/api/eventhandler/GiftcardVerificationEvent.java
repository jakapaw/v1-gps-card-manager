package dev.jakapaw.giftcard.seriesmanager.api.eventhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GiftcardVerificationEvent {

    private String serialNumber;
    private boolean isExist;
}
