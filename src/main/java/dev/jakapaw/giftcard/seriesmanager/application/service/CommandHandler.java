package dev.jakapaw.giftcard.seriesmanager.application.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.seriesmanager.application.command.DeductBalanceCommand;
import dev.jakapaw.giftcard.seriesmanager.application.command.IssueSeriesCommand;
import dev.jakapaw.giftcard.seriesmanager.application.command.TopUpGiftcardCommand;
import dev.jakapaw.giftcard.seriesmanager.application.command.VerifyGiftcardCommand;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardBalanceAdded;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardDeductionFailed;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardDeductionSuccess;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardVerified;
import dev.jakapaw.giftcard.seriesmanager.application.event.SeriesCreated;
import dev.jakapaw.giftcard.seriesmanager.application.exception.GiftcardNotFound;
import dev.jakapaw.giftcard.seriesmanager.domain.Giftcard;
import dev.jakapaw.giftcard.seriesmanager.domain.Series;
import dev.jakapaw.giftcard.seriesmanager.infrastructure.repository.GiftcardRepository;
import dev.jakapaw.giftcard.seriesmanager.infrastructure.repository.SeriesRepository;

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
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener(IssueSeriesCommand.class)
    public void on(IssueSeriesCommand command) {
        // initialize series aggregate, save to database, publish new event
        Series savedSeries = seriesRepository.save(new Series(command));
        applicationEventPublisher.publishEvent(new SeriesCreated(this, savedSeries));
    }

    @EventListener(DeductBalanceCommand.class)
    public void on(DeductBalanceCommand command) {
        try (ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor()) {
            exec.execute(() -> processPayment(command));
        }
    }

    private void processPayment(DeductBalanceCommand command) {
        giftcardRepository.findById(command.getGiftcardSerialNumber())
                .ifPresentOrElse(giftcard -> {
                    Giftcard updated = new Giftcard(
                            giftcard.getSerialNumber(),
                            giftcard.getSeries(),
                            giftcard.getBalance() - command.getBillAmount());
                    giftcardRepository.save(updated);

                    GiftcardDeductionSuccess event = new GiftcardDeductionSuccess(
                        this, command.getPaymentId(), updated.getSerialNumber(), command.getBillAmount(), updated.getBalance());
                    applicationEventPublisher.publishEvent(event);

                }, () -> {
                    GiftcardDeductionFailed event = new GiftcardDeductionFailed(
                            this,
                            command.getPaymentId(), 
                            command.getGiftcardSerialNumber(),
                            command.getBillAmount());
                    applicationEventPublisher.publishEvent(event);
                });
    }

    @EventListener(VerifyGiftcardCommand.class)
    public void on(VerifyGiftcardCommand command) {
        giftcardRepository.findById(command.getGiftcardSerialNumber())
                .ifPresentOrElse(giftcard -> {
                    if (giftcard.getBalance() < command.getBillAmount()) {
                        GiftcardVerified event = new GiftcardVerified(
                            this, command.getPaymentId(), command.getGiftcardSerialNumber(), command.getBillAmount(), true, false);
                        applicationEventPublisher.publishEvent(event);
                    } else {
                        GiftcardVerified event = new GiftcardVerified(
                            this, command.getPaymentId(), command.getGiftcardSerialNumber(), command.getBillAmount(), true, true);
                        applicationEventPublisher.publishEvent(event);
                    }
                }, () -> {
                    GiftcardVerified event = new GiftcardVerified(
                        this, command.getPaymentId(), command.getGiftcardSerialNumber(), command.getBillAmount(), false, false);
                    applicationEventPublisher.publishEvent(event);
                });
    }

    public Double on(TopUpGiftcardCommand command) {
        Giftcard giftcard = giftcardRepository.findById(command.getGiftcardSerialNumber()).orElseThrow(GiftcardNotFound::new);
        giftcard.addBalance(command.getTopUpNominal());
        giftcardRepository.save(giftcard);
        applicationEventPublisher.publishEvent(new GiftcardBalanceAdded(giftcard.getSerialNumber(), command.getTopUpNominal()));
        return giftcard.getBalance();
    }
}
