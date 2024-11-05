package dev.jakapaw.giftcard.seriesmanager.infrastructure.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import dev.jakapaw.giftcard.seriesmanager.application.event.IssueEventBase;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.EventObject;

@Service
public class KafkaProducer {

    KafkaTemplate<String, String> kafkaTemplate;            // use json serializer-deserializer

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener(IssueEventBase.class)
    public void publishIssueEvent(IssueEventBase event) {
        JsonMapper mapper = JsonMapper.builder().build();
        try {
            kafkaTemplate.send("issue-event", mapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
