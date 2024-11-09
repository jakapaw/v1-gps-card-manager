package dev.jakapaw.giftcard.seriesmanager.application;

import dev.jakapaw.giftcard.seriesmanager.application.event.IssueSeriesCommand;
import dev.jakapaw.giftcard.seriesmanager.application.event.SeriesCreated;
import dev.jakapaw.giftcard.seriesmanager.common.SeriesEvent;
import dev.jakapaw.giftcard.seriesmanager.domain.Series;
import dev.jakapaw.giftcard.seriesmanager.infrastructure.repository.GiftcardRepository;
import dev.jakapaw.giftcard.seriesmanager.infrastructure.repository.SeriesRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EventHandler implements ApplicationEventPublisherAware {

    private final SeriesRepository seriesRepository;
    private final GiftcardRepository giftcardRepository;
    private ApplicationEventPublisher applicationEventPublisher;

    public EventHandler(SeriesRepository seriesRepository, GiftcardRepository giftcardRepository) {
        this.seriesRepository = seriesRepository;
        this.giftcardRepository = giftcardRepository;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener(IssueSeriesCommand.class)
    public void on(IssueSeriesCommand command) {
        // initialize series aggregate, save to database, publish new event
        Series newSeries = new Series(command);
        seriesRepository.save(newSeries);
        giftcardRepository.saveAll(newSeries.getGiftcards());
        SeriesEvent<SeriesCreated> seriesCreatedEvent = new SeriesEvent<>(newSeries, new SeriesCreated());
        applicationEventPublisher.publishEvent(seriesCreatedEvent);
    }
}
