package dev.jakapaw.giftcard.seriesmanager.application.event;

import lombok.Getter;

@Getter
public class GiftcardBalanceAdded {
    
    private final String giftcardSerialNumber;
    private final Double topUpNominal;
    
    public GiftcardBalanceAdded(String giftcardSerialNumber, Double topUpNominal) {
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.topUpNominal = topUpNominal;
    }
}
