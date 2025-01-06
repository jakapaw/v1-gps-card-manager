package dev.jakapaw.giftcard.seriesmanager.application;

import dev.jakapaw.giftcard.seriesmanager.application.command.IssueSeriesCommand;
import dev.jakapaw.giftcard.seriesmanager.application.command.ProcessPaymentCommand;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardDeducted;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardDeductionFailed;
import dev.jakapaw.giftcard.seriesmanager.application.event.SeriesCreated;
import dev.jakapaw.giftcard.seriesmanager.domain.Giftcard;
import dev.jakapaw.giftcard.seriesmanager.domain.Series;
import dev.jakapaw.giftcard.seriesmanager.infrastructure.repository.GiftcardRepository;
import dev.jakapaw.giftcard.seriesmanager.infrastructure.repository.SeriesRepository;
import dev.jakapaw.giftcard.seriesmanager.shareddomain.GiftcardEvent;
import dev.jakapaw.giftcard.seriesmanager.shareddomain.SeriesEvent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CommandHandler implements ApplicationEventPublisherAware {

    private final SeriesRepository seriesRepository;
    private final GiftcardRepository giftcardRepository;
    private ApplicationEventPublisher applicationEventPublisher;

    public CommandHandler(SeriesRepository seriesRepository, GiftcardRepository giftcardRepository) {
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
        SeriesEvent<SeriesCreated> seriesCreatedEvent = new SeriesEvent<>(this, new SeriesCreated());
        applicationEventPublisher.publishEvent(seriesCreatedEvent);
    }

    @EventListener(ProcessPaymentCommand.class)
    public void on(ProcessPaymentCommand command) {
        try (ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor()) {
            exec.execute(() -> processPayment(command));
        }
    }

    private void processPayment(ProcessPaymentCommand command) {
        giftcardRepository.findById(command.getGiftcardSerialNumber())
                .ifPresentOrElse(giftcard -> {
                    Giftcard updated = new Giftcard(
                            giftcard.getSerialNumber(),
                            giftcard.getSeries(),
                            giftcard.getBalance() - command.getBillAmount());

                    giftcardRepository.save(updated);

                    GiftcardDeducted event = new GiftcardDeducted(
                            updated.getSerialNumber(), updated.getBalance());
                    applicationEventPublisher.publishEvent(new GiftcardEvent<>(this, event));

                }, () -> {
                    GiftcardDeductionFailed event = new GiftcardDeductionFailed(
                            command.getGiftcardSerialNumber(),
                            command.getBillAmount());
                    applicationEventPublisher.publishEvent(new GiftcardEvent<>(this, event));
                });
    }
}
