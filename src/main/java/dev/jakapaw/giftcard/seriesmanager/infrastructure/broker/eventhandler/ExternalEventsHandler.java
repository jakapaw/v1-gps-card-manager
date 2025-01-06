package dev.jakapaw.giftcard.seriesmanager.infrastructure.broker.eventhandler;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.seriesmanager.application.command.ProcessPaymentCommand;
import dev.jakapaw.giftcard.seriesmanager.application.command.VerifyPaymentCommand;

@Service
public class ExternalEventsHandler implements ApplicationEventPublisherAware {

    ApplicationEventPublisher applicationEventPublisher;

    @KafkaListener(id = "paymentProcessor", topics = "payment.verification", groupId = "paymentConsumer")
    public void listenVerification(SharedPaymentEvent event) {
        VerifyPaymentCommand command = new VerifyPaymentCommand(this, event.getGiftcardSerialNumber(), event.getBillAmount());
        applicationEventPublisher.publishEvent(command);
    }

    @KafkaListener(id = "paymentProcessor", topics = "payment.deduction", groupId = "paymentConsumer")
    public void listenDeduction(SharedPaymentEvent event) {
        ProcessPaymentCommand command = new ProcessPaymentCommand(this, event.getGiftcardSerialNumber(), event.getBillAmount());
        applicationEventPublisher.publishEvent(command);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
