package dev.jakapaw.giftcard.seriesmanager.infrastructure.broker;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardDeducted;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardVerified;
import dev.jakapaw.giftcard.seriesmanager.shareddomain.GiftcardEventWrapper;
import dev.jakapaw.giftcard.seriesmanager.shareddomain.SeriesEvent;

@Service
public class KafkaProducer {

    KafkaTemplate<String, Object> kafkaTemplateJson;

    public KafkaProducer(@Qualifier("kafkaTemplateJson") KafkaTemplate<String, Object> kafkaTemplateJson) {
        this.kafkaTemplateJson = kafkaTemplateJson;
    }

    @EventListener(SeriesEvent.class)
    public void publishIssueEvent(SeriesEvent<?> seriesEvent) {
        kafkaTemplateJson.send("series.event", seriesEvent.getEvent());
    }

    @EventListener(GiftcardEventWrapper.class)
    public void publishVerificationEvent(GiftcardEventWrapper<?> giftcardEvent) {
        if (giftcardEvent.getEvent().getClass() == GiftcardVerified.class) {
            kafkaTemplateJson.send("payment.verification.done", (GiftcardVerified) giftcardEvent.getEvent());
        } else if (giftcardEvent.getEvent().getClass() == GiftcardDeducted.class) {
            kafkaTemplateJson.send("payment.process.done", (GiftcardDeducted) giftcardEvent.getEvent());
        }
    }
}
