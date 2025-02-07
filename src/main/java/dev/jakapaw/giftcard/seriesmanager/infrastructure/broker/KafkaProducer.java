package dev.jakapaw.giftcard.seriesmanager.infrastructure.broker;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardDeductionFailed;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardDeductionSuccess;
import dev.jakapaw.giftcard.seriesmanager.application.event.SeriesCreated;
import dev.jakapaw.giftcard.seriesmanager.infrastructure.broker.eventhandler.SharedPaymentEvent;

@Service
public class KafkaProducer {

    KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishIssueEvent(SeriesCreated event) {
        kafkaTemplate.send("series.event", event);
    }

    public void publishPaymentDeclinedEvent(GiftcardDeductionFailed event) {
        SharedPaymentEvent sharedEvent = new SharedPaymentEvent(
            event.getPaymentId(),
            event.getGiftcardSerialNumber(), 
            event.getBillAmount(), 
            SharedPaymentEvent.PaymentStatus.DECLINED.name());

        kafkaTemplate.send("payment.process.end", sharedEvent);
    }

    public void publishPaymentAcceptedEvent(GiftcardDeductionSuccess event) {
        SharedPaymentEvent sharedEvent = new SharedPaymentEvent(
            event.getPaymentId(),
            event.getGiftcardSerialNumber(), 
            event.getBillAmount(), 
            event.getFinalBalance(),
            SharedPaymentEvent.PaymentStatus.ACCEPTED.name());

        kafkaTemplate.send("payment.process.end", sharedEvent);
    }
}
