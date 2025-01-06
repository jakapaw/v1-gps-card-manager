package dev.jakapaw.giftcard.seriesmanager.application.query;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.seriesmanager.domain.Series;
import dev.jakapaw.giftcard.seriesmanager.infrastructure.repository.SeriesRepository;

@Service
public class SeriesQueryService {

    private final SeriesRepository seriesRepository;

    public SeriesQueryService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public List<Series> findAllSeriesByIssuer(String issuerId) {
        return seriesRepository.findAllByIssuer(issuerId);
    }
}
