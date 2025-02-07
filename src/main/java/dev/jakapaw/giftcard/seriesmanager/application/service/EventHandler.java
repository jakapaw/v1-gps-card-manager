package dev.jakapaw.giftcard.seriesmanager.application.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.seriesmanager.application.command.DeductBalanceCommand;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardBalanceAdded;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardDeductionFailed;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardDeductionSuccess;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardVerified;
import dev.jakapaw.giftcard.seriesmanager.application.event.SeriesCreated;
import dev.jakapaw.giftcard.seriesmanager.infrastructure.broker.KafkaProducer;

@Service
public class EventHandler implements ApplicationEventPublisherAware {
    
    ApplicationEventPublisher applicationEventPublisher;
    KafkaProducer kafkaProducer;

    public EventHandler(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener(SeriesCreated.class)
    public void on(SeriesCreated event) {
        kafkaProducer.publishIssueEvent(event);
    }

    @EventListener(GiftcardVerified.class)
    public void on(GiftcardVerified event) {
        if (event.isExist() && event.isBalanceAvailable()) {
            DeductBalanceCommand command = new DeductBalanceCommand(this, event.getPaymentId(), event.getSerialNumber(), event.getBillAmount());
            applicationEventPublisher.publishEvent(command);
        } else {
            on(new GiftcardDeductionFailed(this, event.getPaymentId(), event.getSerialNumber(), event.getBillAmount()));
        }
    }

    @EventListener(GiftcardDeductionFailed.class)
    public void on(GiftcardDeductionFailed event) {
        kafkaProducer.publishPaymentDeclinedEvent(event);
    }

    @EventListener(GiftcardDeductionSuccess.class)
    public void on(GiftcardDeductionSuccess event) {
        kafkaProducer.publishPaymentAcceptedEvent(event);
    }

    @EventListener(GiftcardBalanceAdded.class)
    public void on(GiftcardBalanceAdded event) {
        // TODO: save to event source db
    } 
}
