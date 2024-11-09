package dev.jakapaw.giftcard.seriesmanager.application.event;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class SeriesCreated {

    private final LocalDateTime timestamp;

    public SeriesCreated() {
        timestamp = LocalDateTime.now(ZoneId.of("UTC"));
    }
}
