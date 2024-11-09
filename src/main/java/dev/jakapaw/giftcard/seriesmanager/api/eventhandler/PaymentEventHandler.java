package dev.jakapaw.giftcard.seriesmanager.api.eventhandler;

import dev.jakapaw.giftcard.seriesmanager.api.dto.GiftcardEvent;
import dev.jakapaw.giftcard.seriesmanager.application.command.VerifyGiftcardCommand;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventHandler implements ApplicationEventPublisherAware {

    ApplicationEventPublisher applicationEventPublisher;

    @KafkaListener(id = "giftcardEventListener", topics = "series.giftcard.verification")
    public void listen(GiftcardEvent event) {
        VerifyGiftcardCommand command = new VerifyGiftcardCommand(this, event.getSerialNumber(), event.isExist());
        applicationEventPublisher.publishEvent(command);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
