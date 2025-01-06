package dev.jakapaw.giftcard.seriesmanager.shareddomain;

import java.util.EventObject;

import lombok.Getter;

@Getter
public class GiftcardEventWrapper<T> extends EventObject {

    T event;

    public GiftcardEventWrapper(Object source, T event) {
        super(source);
        this.event = event;
    }
}
