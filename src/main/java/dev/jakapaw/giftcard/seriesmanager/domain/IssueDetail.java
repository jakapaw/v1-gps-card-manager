package dev.jakapaw.giftcard.seriesmanager.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;

@Embeddable
@Getter
public class IssueDetail {

    private String issuer;
    private int cardIssued;
    private double totalValue;
    private LocalDateTime issueTime;

    public IssueDetail(String issuer, int cardIssued, double totalValue, LocalDateTime issueTime) {
        this.issuer = issuer;
        this.cardIssued = cardIssued;
        this.totalValue = totalValue;
        this.issueTime = issueTime;
    }

    public IssueDetail() {
    }
}
