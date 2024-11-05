package dev.jakapaw.giftcard.seriesmanager.api.rest;

import dev.jakapaw.giftcard.seriesmanager.api.dto.IssueDetailDTO;
import dev.jakapaw.giftcard.seriesmanager.application.commandservice.IssueService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/series")
public class SeriesManagerController {

    IssueService issueService;

    public SeriesManagerController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/issue")
    public String issueNewSeries(@RequestBody IssueDetailDTO issueDetailDTO) {
        return issueService.issueNewSeries(
                issueDetailDTO.getIssuer(),
                issueDetailDTO.getCardIssued(),
                issueDetailDTO.getTotalValue(),
                LocalDateTime.now()
        );
    }
}
