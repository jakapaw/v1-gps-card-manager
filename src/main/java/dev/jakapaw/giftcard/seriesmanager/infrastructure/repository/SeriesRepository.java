package dev.jakapaw.giftcard.seriesmanager.infrastructure.repository;

import dev.jakapaw.giftcard.seriesmanager.domain.Series;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeriesRepository extends CrudRepository<Series, Integer> {

    @Query("select count(s) from Series s where s.issueDetail.issuer=?1")
    Integer countByIssuer(String issuerId);

    @Query("select s from Series s where s.issueDetail.issuer=?1")
    List<Series> findAllByIssuer(String issuerId);
}
