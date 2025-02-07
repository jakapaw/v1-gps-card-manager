package dev.jakapaw.giftcard.seriesmanager.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.jakapaw.giftcard.seriesmanager.domain.GiftcardEvent;
import dev.jakapaw.giftcard.seriesmanager.domain.GiftcardEventId;

public interface GiftcardEventStore extends JpaRepository<GiftcardEvent, GiftcardEventId> {

    @Query(
        nativeQuery = true, 
        value = "SELECT * FROM giftcard_event WHERE giftcard_serial_number = ?1 ORDER BY created_at DESC LIMIT 1")
    Optional<GiftcardEvent> findGiftcardLastEvent(String giftcardSerialNumber);
}