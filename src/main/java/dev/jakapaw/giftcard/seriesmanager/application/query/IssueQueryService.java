package dev.jakapaw.giftcard.seriesmanager.application.query;

import dev.jakapaw.giftcard.seriesmanager.domain.Series;
import dev.jakapaw.giftcard.seriesmanager.infrastructure.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueQueryService {

    private final SeriesRepository seriesRepository;

    public IssueQueryService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public List<Series> findAllSeriesByIssuer(String issuerId) {
        return seriesRepository.findAllByIssuer(issuerId);
    }
}
