package dev.jakapaw.giftcard.seriesmanager.api.dto;

import lombok.Getter;

@Getter
public class IssueDetailDTO {

    String issuer;
    int cardIssued;
    double totalValue;

    public IssueDetailDTO(String issuer, int cardIssued, double totalValue) {
        this.issuer = issuer;
        this.cardIssued = cardIssued;
        this.totalValue = totalValue;
    }

}
