package dev.jakapaw.giftcard.seriesmanager.application.event;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class SeriesCreated extends ApplicationEvent {

    private final LocalDateTime timestamp;

    public SeriesCreated(Object source) {
        super(source);
        timestamp = LocalDateTime.now(ZoneId.of("UTC"));
    }
}
