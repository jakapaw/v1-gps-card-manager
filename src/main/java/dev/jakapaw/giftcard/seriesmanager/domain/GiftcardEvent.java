package dev.jakapaw.giftcard.seriesmanager.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;

@Entity
@IdClass(GiftcardEventId.class)
@Getter
public class GiftcardEvent {
    
    @Id
    private String giftcardSerialNumber;
    @Id
    private Integer version;
    
    @JdbcTypeCode(SqlTypes.JSON)
    private String eventData;     // as JSON

    private String eventClassName;
    private LocalDateTime createdAt;
    
    public GiftcardEvent(String giftcardSerialNumber, Integer version, String eventClassName, String eventData) {
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.version = version;
        this.eventClassName = eventClassName;
        this.eventData = eventData;
        this.createdAt = LocalDateTime.now();
    }

    public GiftcardEvent() {
    }

    public void setNewEvent(String eventClassName, String eventData) {
        this.eventClassName = eventClassName;
        this.eventData = eventData;
        this.createdAt = LocalDateTime.now();
        this.version++;
    }
}
