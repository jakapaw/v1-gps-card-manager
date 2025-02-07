package dev.jakapaw.giftcard.seriesmanager.application.command;

import lombok.Getter;

@Getter
public class TopUpGiftcardCommand {
    
    private final String giftcardSerialNumber;
    private final Double topUpNominal;
    
    public TopUpGiftcardCommand(String giftcardSerialNumber, Double topUpNominal) {
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.topUpNominal = topUpNominal;
    }
}
