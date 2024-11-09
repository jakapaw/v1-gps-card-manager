package dev.jakapaw.giftcard.seriesmanager.common;

import lombok.Getter;

import java.util.EventObject;

@Getter
public class GiftcardEvent<T> extends EventObject {

    T event;

    public GiftcardEvent(Object source, T event) {
        super(source);
        this.event = event;
    }
}
