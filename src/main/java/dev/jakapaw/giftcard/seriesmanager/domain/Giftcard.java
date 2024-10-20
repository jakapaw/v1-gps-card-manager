package dev.jakapaw.giftcard.seriesmanager.domain;

import dev.jakapaw.giftcard.seriesmanager.application.event.SeriesCreated;
import jakarta.persistence.*;
import org.springframework.context.event.EventListener;

@Entity
public class Giftcard {

    @Id
    private Long serialNumber;            // Example: 1234567891234567 (16 digits)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    private Series series;

    private Double balance;

    public Giftcard(Long serialNumber, Series series, Double balance) {
        this.serialNumber = serialNumber;
        this.series = series;
        this.balance = balance;
    }

    @EventListener
    public void on(SeriesCreated event) {
        // publish event to kafka topic
        // save in event datastore
    }
}
