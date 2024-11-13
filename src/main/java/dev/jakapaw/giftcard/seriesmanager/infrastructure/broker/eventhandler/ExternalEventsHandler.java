package dev.jakapaw.giftcard.seriesmanager.infrastructure.broker.eventhandler;

import dev.jakapaw.giftcard.seriesmanager.application.command.ProcessPaymentCommand;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ExternalEventsHandler implements ApplicationEventPublisherAware {

    ApplicationEventPublisher applicationEventPublisher;

    @KafkaListener(id = "paymentProcessor", topics = "series.payment", groupId = "paymentConsumer")
    public void listen(SharedPaymentEvent event) {
        ProcessPaymentCommand command = new ProcessPaymentCommand(this, event.getGiftcardSerialNumber(), event.getBillAmount());
        applicationEventPublisher.publishEvent(command);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
