package dev.jakapaw.giftcard.seriesmanager.application.event;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.EventObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.jakapaw.giftcard.seriesmanager.domain.Series;
import lombok.Getter;

@Getter
public class SeriesCreated extends EventObject {

    private final String seriesId;
    @JsonIgnore
    private final Series series;
    private final LocalDateTime timestamp;

    public SeriesCreated(Object source, Series series) {
        super(source);
        this.series = series;
        timestamp = LocalDateTime.now(ZoneId.of("UTC"));
        seriesId = series.getSeriesId();
    }
}
