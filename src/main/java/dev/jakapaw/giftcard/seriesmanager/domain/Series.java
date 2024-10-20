package dev.jakapaw.giftcard.seriesmanager.domain;

import dev.jakapaw.giftcard.seriesmanager.application.event.IssueSeriesCommand;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

@Entity
public class Series {

    @Id
    private final String seriesId;                    // Example: SAB001

    @Embedded
    private final IssueDetail issueDetail;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "series", fetch = FetchType.EAGER)
    private final List<Giftcard> giftcards;

    private final LocalDateTime expiryTime;

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
        RandomGenerator randomGenerator = new Random();

        for (int i = 0; i < cardIssued; i++) {
            long serialNumber = (long) randomGenerator.nextDouble(10 ^ 16, 10 ^ 17);
            double initialBalance = totalValue / cardIssued;
            Giftcard giftcard = new Giftcard(serialNumber, this, initialBalance);
            result.add(giftcard);
        }

        return result;
    }
}

