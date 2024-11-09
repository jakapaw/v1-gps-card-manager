package dev.jakapaw.giftcard.seriesmanager.infrastructure.broker;

import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardDeducted;
import dev.jakapaw.giftcard.seriesmanager.application.event.GiftcardVerified;
import dev.jakapaw.giftcard.seriesmanager.common.GiftcardEvent;
import dev.jakapaw.giftcard.seriesmanager.common.SeriesEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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

    @EventListener(GiftcardEvent.class)
    public void publishVerificationEvent(GiftcardEvent<?> giftcardEvent) {
        if (giftcardEvent.getEvent().getClass() == GiftcardVerified.class) {
            kafkaTemplateJson.send("series.giftcard.verification", giftcardEvent);
        } else if (giftcardEvent.getEvent().getClass() == GiftcardDeducted.class) {
            kafkaTemplateJson.send("series.giftcard.payment", giftcardEvent);
        }
    }
}
