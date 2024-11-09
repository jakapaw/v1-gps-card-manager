package dev.jakapaw.giftcard.seriesmanager.api.eventhandler;

import dev.jakapaw.giftcard.seriesmanager.application.command.DeductBalanceCommand;
import dev.jakapaw.giftcard.seriesmanager.application.command.VerifyGiftcardCommand;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventsHandler implements ApplicationEventPublisherAware {

    ApplicationEventPublisher applicationEventPublisher;

    @KafkaListener(id = "verification", topics = "series.giftcard.verification", groupId = "verificationConsumers")
    public void listen(GiftcardVerificationEvent event) {
        VerifyGiftcardCommand command = new VerifyGiftcardCommand(this, event.getSerialNumber(), event.isExist());
        applicationEventPublisher.publishEvent(command);
    }

    @KafkaListener(id = "payment", topics = "series.giftcard.payment", groupId = "paymentConsumer")
    public void listen(PaymentEvent event) {
        DeductBalanceCommand command = new DeductBalanceCommand(this, event.getSerialNumber(), event.getBilled());
        applicationEventPublisher.publishEvent(command);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
