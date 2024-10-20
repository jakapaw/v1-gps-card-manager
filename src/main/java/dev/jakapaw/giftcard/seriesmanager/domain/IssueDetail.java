package dev.jakapaw.giftcard.seriesmanager.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;

@Embeddable
@Getter
public class IssueDetail {

    private final String issuer;
    private final int cardIssued;
    private final double totalValue;
    private final LocalDateTime issueTime;

    public IssueDetail(String issuer, int cardIssued, double totalValue, LocalDateTime issueTime) {
        this.issuer = issuer;
        this.cardIssued = cardIssued;
        this.totalValue = totalValue;
        this.issueTime = issueTime;
    }
}
