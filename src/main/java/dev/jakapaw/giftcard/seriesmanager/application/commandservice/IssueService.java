package dev.jakapaw.giftcard.seriesmanager.application.commandservice;

import dev.jakapaw.giftcard.seriesmanager.application.event.IssueSeriesCommand;
import dev.jakapaw.giftcard.seriesmanager.application.event.SeriesCreated;
import dev.jakapaw.giftcard.seriesmanager.domain.Series;
import dev.jakapaw.giftcard.seriesmanager.infrastructure.repository.SeriesRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class IssueService implements ApplicationEventPublisherAware {

    private final SeriesRepository repository;
    private ApplicationEventPublisher applicationEventPublisher;

    public IssueService(SeriesRepository repository) {
        this.repository = repository;
    }

    public String issueNewSeries(
            String issuer,
            int cardIssued,
            double totalValue,
            LocalDateTime expiryTime
    ) {
        String seriesId = generateSeriesId(issuer);
        IssueSeriesCommand command = new IssueSeriesCommand(
                this,
                seriesId,
                issuer,
                cardIssued,
                totalValue,
                expiryTime
        );
        applicationEventPublisher.publishEvent(command);
        return seriesId;
    }

    private String generateSeriesId(String issuer) {
        int issueCount = repository.countByIssuer(issuer);
        return issuer + String.format("%02d", issueCount);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
