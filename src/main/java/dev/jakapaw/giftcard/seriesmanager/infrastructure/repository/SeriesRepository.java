package dev.jakapaw.giftcard.seriesmanager.infrastructure.repository;

import dev.jakapaw.giftcard.seriesmanager.domain.Series;
import org.springframework.data.repository.CrudRepository;

public interface SeriesRepository extends CrudRepository<Series, Integer> {

    Integer countByIssuer(String issuer);
}
