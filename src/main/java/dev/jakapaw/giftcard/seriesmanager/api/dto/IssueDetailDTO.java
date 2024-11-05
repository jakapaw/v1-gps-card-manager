package dev.jakapaw.giftcard.seriesmanager.api.dto;

public class IssueDetailDTO {

    public String issuer;
    public int cardIssued;
    public double totalValue;
    public ExpiryDuration expiryDuration;

    public IssueDetailDTO(String issuer, int cardIssued, double totalValue, ExpiryDuration expiryDuration) {
        this.issuer = issuer;
        this.cardIssued = cardIssued;
        this.totalValue = totalValue;
        this.expiryDuration = expiryDuration;
    }

    public static class ExpiryDuration {
        public int d;
        public int m;
        public int y;

        public ExpiryDuration(int d, int m, int y) {
            this.d = d;
            this.m = m;
            this.y = y;
        }
    }
}

