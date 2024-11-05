package dev.jakapaw.giftcard.seriesmanager.infrastructure.repository;

import dev.jakapaw.giftcard.seriesmanager.domain.Giftcard;
import org.springframework.data.repository.CrudRepository;

public interface GiftcardRepository extends CrudRepository<Giftcard, Long> {
}
