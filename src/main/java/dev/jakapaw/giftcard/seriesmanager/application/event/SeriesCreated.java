package dev.jakapaw.giftcard.seriesmanager.application.event;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Data
public class SeriesCreated {

    private final LocalDateTime timestamp;

    public SeriesCreated() {
        timestamp = LocalDateTime.now(ZoneId.of("UTC"));
    }
}
