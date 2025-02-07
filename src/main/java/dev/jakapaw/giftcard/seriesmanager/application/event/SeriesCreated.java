package dev.jakapaw.giftcard.seriesmanager.application.event;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.EventObject;

import lombok.Getter;

@Getter
public class SeriesCreated extends EventObject {

    private final LocalDateTime timestamp;

    public SeriesCreated(Object source) {
        super(source);
        timestamp = LocalDateTime.now(ZoneId.of("UTC"));
    }
}
