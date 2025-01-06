package dev.jakapaw.giftcard.seriesmanager.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.jakapaw.giftcard.seriesmanager.application.command.IssueService;
import dev.jakapaw.giftcard.seriesmanager.application.query.SeriesQueryService;
import dev.jakapaw.giftcard.seriesmanager.domain.Series;
import dev.jakapaw.giftcard.seriesmanager.rest.dto.IssueDetailDTO;

@RestController
@RequestMapping("/api/series")
public class SeriesManagerController {

    IssueService issueService;
    SeriesQueryService issueQueryService;

    public SeriesManagerController(IssueService issueService, SeriesQueryService issueQueryService) {
        this.issueService = issueService;
        this.issueQueryService = issueQueryService;
    }

    @PostMapping("/issue")
    public String issueNewSeries(@RequestBody IssueDetailDTO issueDetailDTO) {
        LocalDateTime expiry = LocalDateTime.now()
                .plusDays(issueDetailDTO.expiryDuration.d)
                .plusMonths(issueDetailDTO.expiryDuration.m)
                .plusYears(issueDetailDTO.expiryDuration.y);

        return issueService.issueNewSeries(
                issueDetailDTO.issuer,
                issueDetailDTO.cardIssued,
                issueDetailDTO.totalValue,
                expiry
        );
    }

    @GetMapping("/issue/{issuerId}")
    public List<Series> showAllIssueByIssuer(@PathVariable("issuerId") String issuerId) {
        return issueQueryService.findAllSeriesByIssuer(issuerId);
    }
}
