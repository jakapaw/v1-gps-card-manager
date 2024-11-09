package dev.jakapaw.giftcard.seriesmanager.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GiftcardEvent {

    private String serialNumber;
    private boolean isExist;
}
