package dev.jakapaw.giftcard.seriesmanager.application.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.jakapaw.giftcard.seriesmanager.application.command.DeductBalanceCommand;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardBalanceAdded;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardCreated;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardDeductionFailed;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardDeductionSuccess;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardVerified;
import dev.jakapaw.giftcard.seriesmanager.application.event.SeriesCreated;
import dev.jakapaw.giftcard.seriesmanager.application.exception.GiftcardNotFound;
import dev.jakapaw.giftcard.seriesmanager.domain.Giftcard;
import dev.jakapaw.giftcard.seriesmanager.domain.GiftcardEvent;
import dev.jakapaw.giftcard.seriesmanager.infrastructure.broker.KafkaProducer;
import dev.jakapaw.giftcard.seriesmanager.infrastructure.repository.GiftcardEventStore;

@Service
public class EventHandler implements ApplicationEventPublisherAware {
    
    ApplicationEventPublisher applicationEventPublisher;
    KafkaProducer kafkaProducer;
    GiftcardEventStore giftcardEventStore;
    ObjectMapper om;

    public EventHandler(KafkaProducer kafkaProducer, GiftcardEventStore giftcardEventStore, ObjectMapper om) {
        this.kafkaProducer = kafkaProducer;
        this.giftcardEventStore = giftcardEventStore;
        this.om = om;
    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener(SeriesCreated.class)
    public void on(SeriesCreated event) {
        for (Giftcard giftcard : event.getSeries().getGiftcards()) {
            applicationEventPublisher.publishEvent(new GiftcardCreated(this, giftcard.getSerialNumber(), giftcard.getBalance()));
        }
        kafkaProducer.publishIssueEvent(event);
    }

    @EventListener(GiftcardCreated.class)
    public void on(GiftcardCreated event) throws JsonProcessingException {
        String eventDataJson = om.writeValueAsString(event);
        GiftcardEvent giftcardEvent = new GiftcardEvent(
            event.getGiftcardSerialNumber(), 
            1, 
            event.getClass().getSimpleName(), 
            eventDataJson);
        giftcardEventStore.save(giftcardEvent);
    }

    @EventListener(GiftcardVerified.class)
    public void on(GiftcardVerified event) throws JsonProcessingException {
        if (event.isExist() && event.isBalanceAvailable()) {
            DeductBalanceCommand command = new DeductBalanceCommand(this, event.getPaymentId(), event.getSerialNumber(), event.getBillAmount());
            applicationEventPublisher.publishEvent(command);
        } else {
            on(new GiftcardDeductionFailed(this, event.getPaymentId(), event.getSerialNumber(), event.getBillAmount()));
        }
    }

    @EventListener(GiftcardDeductionFailed.class)
    public void on(GiftcardDeductionFailed event) throws JsonProcessingException {
        GiftcardEvent giftcardEvent = giftcardEventStore.findGiftcardLastEvent(event.getGiftcardSerialNumber())
        .orElseThrow(GiftcardNotFound::new);
        String eventDataJson = om.writeValueAsString(event);
        giftcardEvent.setNewEvent(event.getClass().getSimpleName(), eventDataJson);
        giftcardEventStore.save(giftcardEvent);

        kafkaProducer.publishPaymentDeclinedEvent(event);
    }

    @EventListener(GiftcardDeductionSuccess.class)
    public void on(GiftcardDeductionSuccess event) throws JsonProcessingException {
        GiftcardEvent giftcardEvent = giftcardEventStore.findGiftcardLastEvent(event.getGiftcardSerialNumber())
        .orElseThrow(GiftcardNotFound::new);
        String eventDataJson = om.writeValueAsString(event);
        giftcardEvent.setNewEvent(event.getClass().getSimpleName(), eventDataJson);
        giftcardEventStore.save(giftcardEvent);

        kafkaProducer.publishPaymentAcceptedEvent(event);
    }

    @EventListener(GiftcardBalanceAdded.class)
    public void on(GiftcardBalanceAdded event) throws JsonMappingException, JsonProcessingException {
        GiftcardEvent giftcardEvent = giftcardEventStore.findGiftcardLastEvent(event.getGiftcardSerialNumber())
            .orElseThrow(GiftcardNotFound::new);
    
        String eventDataJson = om.writeValueAsString(event);
        giftcardEvent.setNewEvent(event.getClass().getSimpleName(), eventDataJson);
        giftcardEventStore.save(giftcardEvent);
    } 
}
