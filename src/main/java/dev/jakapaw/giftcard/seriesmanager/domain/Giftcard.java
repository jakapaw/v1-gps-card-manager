package dev.jakapaw.giftcard.seriesmanager.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.ToString;

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

    public void addBalance(Double nominal) {
        this.balance += nominal;
    }
}
