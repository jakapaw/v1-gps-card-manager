package dev.jakapaw.giftcard.seriesmanager.domain;

import dev.jakapaw.giftcard.seriesmanager.application.event.SeriesCreated;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.event.EventListener;

@Entity
@ToString
@Getter
public class Giftcard {

    @Id
    private String serialNumber;            // Example: 1234567891234567 (16 digits)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    private Series series;

    private Double balance;

    public Giftcard(String serialNumber, Series series, Double balance) {
        this.serialNumber = serialNumber;
        this.series = series;
        this.balance = balance;
    }

    public Giftcard() {
    }
}
