package dev.jakapaw.giftcard.seriesmanager.common;

import lombok.Getter;

import java.util.EventObject;

@Getter
public class SeriesEvent<T> extends EventObject {

    T event;

    public SeriesEvent(Object source, T event) {
        super(source);
        this.event = event;
    }

}
