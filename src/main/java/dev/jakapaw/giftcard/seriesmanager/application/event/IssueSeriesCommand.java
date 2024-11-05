package dev.jakapaw.giftcard.seriesmanager.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class IssueSeriesCommand extends IssueEventBase {

    private final String seriesId;
    private final String issuer;
    private final int cardIssued;
    private final double totalValue;
    private final LocalDateTime expiryTime;

    public IssueSeriesCommand(Object source, String seriesId, String issuer, int cardIssued, double totalValue, LocalDateTime expiryTime) {
        super(source);
        this.seriesId = seriesId;
        this.issuer = issuer;
        this.cardIssued = cardIssued;
        this.totalValue = totalValue;
        this.expiryTime = expiryTime;
    }
}
