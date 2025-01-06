package dev.jakapaw.giftcard.seriesmanager.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dev.jakapaw.giftcard.seriesmanager.domain.Series;

public interface SeriesRepository extends CrudRepository<Series, String> {

    @Query("select count(s) from Series s where s.issueDetail.issuer=?1")
    Integer countByIssuer(String issuerId);

    @Query("select s from Series s where s.issueDetail.issuer=?1")
    List<Series> findAllByIssuer(String issuerId);
}
