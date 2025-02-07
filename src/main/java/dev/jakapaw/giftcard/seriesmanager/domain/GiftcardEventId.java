package dev.jakapaw.giftcard.seriesmanager.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GiftcardEventId {

    private String giftcardSerialNumber;
    private Integer version;
}
