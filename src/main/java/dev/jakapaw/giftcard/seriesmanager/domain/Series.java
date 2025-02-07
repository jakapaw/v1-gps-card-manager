package dev.jakapaw.giftcard.seriesmanager.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

import dev.jakapaw.giftcard.seriesmanager.application.command.IssueSeriesCommand;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Series {

    @Id
    private String seriesId;                    // Example: SAB001

    @Embedded
    private IssueDetail issueDetail;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Giftcard> giftcards;

    private LocalDateTime expiryTime;

    public Series() {
    }

    public Series(IssueSeriesCommand command) {
        this.seriesId = command.getSeriesId();
        this.issueDetail = new IssueDetail(command.getIssuer(), command.getCardIssued(), command.getTotalValue(), LocalDateTime.now());
        this.expiryTime = command.getExpiryTime();
        giftcards = createGiftcards();
    }

    private List<Giftcard> createGiftcards() {
        int cardIssued = this.issueDetail.getCardIssued();
        double totalValue = this.issueDetail.getTotalValue();

        ArrayList<Giftcard> result = new ArrayList<>(cardIssued);

        for (int i = 0; i < cardIssued; i++) {
            double initialBalance = totalValue / cardIssued;
            Giftcard giftcard = new Giftcard(
                    generateGiftcardSerial(),
                    this,
                    initialBalance);
            result.add(giftcard);
        }
        return result;
    }

    private String generateGiftcardSerial() {
        RandomGenerator randomGenerator = new Random();
        long n1 = randomGenerator.nextLong(1000,10000);
        long n2 = randomGenerator.nextLong(1000,10000);
        long n3 = randomGenerator.nextLong(1000,10000);
        long n4 = randomGenerator.nextLong(1000,10000);
        return  Long.toString(n1)+"-"+ Long.toString(n2) +"-"+ Long.toString(n3)+"-"+ Long.toString(n4);
    }
}

