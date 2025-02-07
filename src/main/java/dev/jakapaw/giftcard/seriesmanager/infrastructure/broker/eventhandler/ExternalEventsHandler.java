package dev.jakapaw.giftcard.seriesmanager.infrastructure.broker.eventhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.jakapaw.giftcard.seriesmanager.application.command.VerifyGiftcardCommand;

@Service
public class ExternalEventsHandler implements ApplicationEventPublisherAware {

    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    ObjectMapper om;

    @KafkaListener(id = "payment-process", topics = "payment.process.start")
    public void listenVerification(String message) throws JsonMappingException, JsonProcessingException {
        SharedPaymentEvent event = om.readValue(message, SharedPaymentEvent.class);
        VerifyGiftcardCommand command = new VerifyGiftcardCommand(this, event.getPaymentId(), event.getGiftcardSerialNumber(), event.getBillAmount());
        applicationEventPublisher.publishEvent(command);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
