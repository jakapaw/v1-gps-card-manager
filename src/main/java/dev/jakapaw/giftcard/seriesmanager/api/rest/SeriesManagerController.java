package dev.jakapaw.giftcard.seriesmanager.api.rest;

import dev.jakapaw.giftcard.seriesmanager.api.dto.IssueDetailDTO;
import dev.jakapaw.giftcard.seriesmanager.application.commandservice.IssueService;
import dev.jakapaw.giftcard.seriesmanager.application.queryservice.IssueQueryService;
import dev.jakapaw.giftcard.seriesmanager.domain.Series;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/api/series")
public class SeriesManagerController {

    IssueService issueService;
    IssueQueryService issueQueryService;

    public SeriesManagerController(IssueService issueService, IssueQueryService issueQueryService) {
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
